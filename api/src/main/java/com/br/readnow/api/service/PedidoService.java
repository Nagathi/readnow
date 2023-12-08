package com.br.readnow.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.readnow.api.dto.LivroItemDTO;
import com.br.readnow.api.dto.PedidoDTO;
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

    public Iterable<PedidoModel> listarPedidos() {
        return pedidoRepository.findAll();
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
            pedidoModel.setDataEntregaPrevista("20/12/2023");
            pedidoModel.setDataPedido("07/12/2023");
            pedidoModel.setEndereco(enderecOptional.get());
            pedidoModel.setLivros(livrosCarrinho);
            pedidoModel.setValorTotal(pedido.getValorTotal());
            pedidoModel.setUsuario(usOptional.get());
            pedidoRepository.save(pedidoModel);
            return ResponseEntity.ok(pedidoModel.getCodigo());

        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
