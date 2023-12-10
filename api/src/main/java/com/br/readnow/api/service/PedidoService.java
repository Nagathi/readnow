package com.br.readnow.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.LivroQuantidadeDTO;
import com.br.readnow.api.dto.PedidoDTO;
import com.br.readnow.api.dto.PedidoEntregueDTO;
import com.br.readnow.api.model.CarrinhoModel;
import com.br.readnow.api.model.CartaoModel;
import com.br.readnow.api.model.EnderecoModel;
import com.br.readnow.api.model.LivroItemModel;
import com.br.readnow.api.model.PedidoModel;
import com.br.readnow.api.model.UsuarioModel;
import com.br.readnow.api.repository.CarrinhoRepository;
import com.br.readnow.api.repository.CartaoRepository;
import com.br.readnow.api.repository.EnderecoRepository;
import com.br.readnow.api.repository.LivroItemRepository;
import com.br.readnow.api.repository.LivroRepository;
import com.br.readnow.api.repository.PedidoRepository;
import com.br.readnow.api.repository.UsuarioRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LivroItemRepository livroItemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        Optional<UsuarioModel> usOptional = usuarioRepository.findByEmail(pedido.getEmail());
        Optional<CarrinhoModel> carrinhOptional = carrinhoRepository.findByClienteCodigo(usOptional.get().getCodigo());
        List<LivroItemModel> livrosCarrinho = livroItemRepository
                .findAllByCarrinhoCodigo(carrinhOptional.get().getCodigo());

        if (usOptional.isPresent()) {
            PedidoModel pedidoModel = new PedidoModel();
            pedidoModel.setCartao(cartOptional.get());

            LocalDate dataAtual = LocalDate.now();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataPedidoFormatada = dataAtual.format(formatter);

            pedidoModel.setDataEntregaPrevista(dataPedidoFormatada);
            pedidoModel.setDataPedido("07/12/2023");
            pedidoModel.setEndereco(enderecOptional.get());
            pedidoModel.setLivros(livrosCarrinho);
            pedidoModel.setValorTotal(pedido.getValorTotal());
            pedidoModel.setUsuario(usOptional.get());
            pedidoModel.setEntregue(false);

            for (LivroQuantidadeDTO livroQuantidadeDTO : pedido.getLivros()) {
                LivroItemModel livroItem = new LivroItemModel();
                livroItem.setQuantidade(livroQuantidadeDTO.getQuantidade());
                livroItem.setLivro(livroRepository.findById(livroQuantidadeDTO.getCodigoLivro()).orElse(null));
                livroItem.setCarrinho(carrinhOptional.orElse(null));
                livroItem.setPedido(pedidoModel); // Definindo a referência ao PedidoModel

                livrosCarrinho.add(livroItem);
                
            }

            pedidoModel.setLivros(livrosCarrinho);
            pedidoRepository.save(pedidoModel);


            return ResponseEntity.ok(pedidoModel.getCodigo());

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<PedidoEntregueDTO> listarPedidosEntreguesPorUsuario(String emailUsuario) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(emailUsuario);
    
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            List<PedidoModel> pedidos = pedidoRepository.findByUsuarioAndEntregueTrue(usuario);
            
            List<PedidoEntregueDTO> pedidosDTO = new ArrayList<PedidoEntregueDTO>();

            for(PedidoModel pedido : pedidos){
                PedidoEntregueDTO pedidoDTO = new PedidoEntregueDTO();

                pedidoDTO.setCodigo(pedido.getCodigo());
                List<LivroItemModel> livroItemModel = livroItemRepository.findAllByPedidoCodigo(pedido.getCodigo());

                if (!livroItemModel.isEmpty()) {
                    pedidoDTO.setImagem(livroItemModel.get(0).getLivro().getImagem());
                    pedidoDTO.setTitulo(livroItemModel.get(0).getLivro().getTitulo());
                }
                
                pedidoDTO.setData(pedido.getDataPedido());
                pedidosDTO.add(pedidoDTO);
            }

            return pedidosDTO;
        } else {
            return new ArrayList<>();
        }
    }

}
