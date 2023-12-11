package com.br.readnow.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.AjudaDTO;
import com.br.readnow.api.dto.LivroQuantidadeDTO;
import com.br.readnow.api.dto.PedidoDTO;
import com.br.readnow.api.dto.PedidoEntregueDTO;
import com.br.readnow.api.dto.PedidoPendenteDTO;
import com.br.readnow.api.model.CartaoModel;
import com.br.readnow.api.model.EnderecoModel;
import com.br.readnow.api.model.LivroPedidoModel;
import com.br.readnow.api.model.PedidoModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.CartaoRepository;
import com.br.readnow.api.repository.EnderecoRepository;
import com.br.readnow.api.repository.LivroRepository;
import com.br.readnow.api.repository.PedidoRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LivroRepository livroRepository;

    public Iterable<PedidoDTO> listarPedidos() {
        Iterable<PedidoModel> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        
        for (PedidoModel pedido : pedidos) {
            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setDataPedido(pedido.getDataPedido());
            pedidoDTO.setValorTotal(pedido.getValorTotal());
            pedidoDTO.setDataEntregaPrevista(pedido.getDataEntregaPrevista());
            pedidoDTO.setEntregue(pedido.isEntregue());
            pedidoDTO.setEmail(pedido.getUsuario().getEmail());

            if (pedido.getCartao() != null) {
                pedidoDTO.setCodigoCartao(pedido.getCartao().getCodigo());
            }

            if (pedido.getEndereco() != null) {
                pedidoDTO.setCodigoEndereco(pedido.getEndereco().getCodigo());
            }

            pedidosDTO.add(pedidoDTO);
        }
        
        return pedidosDTO;
    }

    public ResponseEntity<Long> salvarPedido(PedidoDTO pedido) {
        Optional<CartaoModel> cartOptional = cartaoRepository.findById(pedido.getCodigoCartao());
        Optional<EnderecoModel> enderecOptional = enderecoRepository.findById(pedido.getCodigoEndereco());
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(pedido.getEmail());
        List<LivroPedidoModel> livrosPedido = new ArrayList<LivroPedidoModel>();

        if (usuarioOptional.isPresent()) {
            PedidoModel pedidoModel = new PedidoModel();
            pedidoModel.setCartao(cartOptional.get());

            LocalDate dataAtual = LocalDate.now();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataPedidoFormatada = dataAtual.format(formatter);

            pedidoModel.setDataEntregaPrevista(dataPedidoFormatada);
            pedidoModel.setDataPedido(dataPedidoFormatada);
            pedidoModel.setEndereco(enderecOptional.get());
            pedidoModel.setLivrosPedido(livrosPedido);
            pedidoModel.setValorTotal(pedido.getValorTotal());
            pedidoModel.setUsuario(usuarioOptional.get());
            pedidoModel.setEntregue(false);

            for (LivroQuantidadeDTO livroQuantidadeDTO : pedido.getLivros()) {
                LivroPedidoModel livroPedidoModel = new LivroPedidoModel();
                livroPedidoModel.setQuantidade(livroQuantidadeDTO.getQuantidade());
                livroPedidoModel.setLivro(livroRepository.findById(livroQuantidadeDTO.getCodigoLivro()).orElse(null));
                livroPedidoModel.setPedido(pedidoModel);
                livroPedidoModel.setUsuario(usuarioOptional.get());

                livrosPedido.add(livroPedidoModel);
            }

            pedidoModel.setLivrosPedido(livrosPedido);
            pedidoRepository.save(pedidoModel);


            return ResponseEntity.ok(pedidoModel.getCodigo());

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<PedidoEntregueDTO> listarLivrosPedidosPorUsuario(String token) {

        String email = tokenService.validarToken(token);

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);
        
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            List<PedidoModel> pedidos = pedidoRepository.findByUsuarioAndEntregueTrue(usuario);
            
            List<PedidoEntregueDTO> pedidosDTO = new ArrayList<>();
            
            for (PedidoModel pedido : pedidos) {
                PedidoEntregueDTO pedidoDTO = new PedidoEntregueDTO();
                pedidoDTO.setCodigo(pedido.getCodigo());
                pedidoDTO.setData(pedido.getDataPedido());
    
                List<LivroPedidoModel> livrosPedidos = pedido.getLivrosPedido();
                
                for (LivroPedidoModel livroPedido : livrosPedidos) {
                    PedidoEntregueDTO pedidoEntregueDTO = new PedidoEntregueDTO();
                    pedidoEntregueDTO.setCodigo(livroPedido.getPedido().getCodigo());
                    pedidoEntregueDTO.setCodigoLivro(livroPedido.getLivro().getCodigo());
                    pedidoEntregueDTO.setImagem(livroPedido.getLivro().getImagem());
                    pedidoEntregueDTO.setTitulo(livroPedido.getLivro().getTitulo());
                    pedidoEntregueDTO.setData(livroPedido.getPedido().getDataPedido());
                    pedidosDTO.add(pedidoEntregueDTO);
                }
            }
            
            return pedidosDTO;
        } else {
            return new ArrayList<>();
        }
    }

    public List<PedidoPendenteDTO> listarLivrosPendentes(String token){
        String email = tokenService.validarToken(token);

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);
        
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            List<PedidoModel> pedidos = pedidoRepository.findByUsuarioAndEntregueFalse(usuario);
            
            List<PedidoPendenteDTO> pedidosDTO = new ArrayList<>();
            
            for (PedidoModel pedido : pedidos) {
                PedidoPendenteDTO pedidoDTO = new PedidoPendenteDTO();
                pedidoDTO.setCodigo(pedido.getCodigo());
                pedidoDTO.setImagem(pedido.getLivrosPedido().get(0).getLivro().getImagem());
                pedidoDTO.setDataEntrega(pedido.getDataEntregaPrevista());

                String dataPedido = pedido.getDataPedido();
                String[] partes = dataPedido.split("/");
                String mesDia = partes[0] + "/" + partes[1];

                pedidoDTO.setData(mesDia);
                pedidoDTO.setTitulo(pedido.getLivrosPedido().get(0).getLivro().getTitulo());
                pedidosDTO.add(pedidoDTO);
            }
            
            return pedidosDTO;
        } else {
            return new ArrayList<>();
        }
    }

    public List<AjudaDTO> listarPedidos(String token){
        String email = tokenService.validarToken(token);
        
        List<AjudaDTO> pedidosDTO = new ArrayList<AjudaDTO>();

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);

        if(usuarioOptional.isPresent()){
            List<PedidoModel> pedidos = pedidoRepository.findAllByUsuario(usuarioOptional.get());

            if(!pedidos.isEmpty()){
                for(PedidoModel pedido : pedidos){
                    AjudaDTO pedidoDTO = new AjudaDTO();

                    pedidoDTO.setCodigo(pedido.getCodigo());
                    pedidoDTO.setImagem(pedido.getLivrosPedido().get(0).getLivro().getImagem());
                    pedidoDTO.setTitulo(pedido.getLivrosPedido().get(0).getLivro().getTitulo());

                    String dataPedido = pedido.getDataPedido();
                    String[] partes = dataPedido.split("/");
                    String diaMes = partes[0] + "/" + partes[1];
                    
                    pedidoDTO.setDiaMes(diaMes);
                    pedidoDTO.setAno(partes[2]);

                    pedidosDTO.add(pedidoDTO);
                }
            }

        }

        return pedidosDTO;
    }
        
}
