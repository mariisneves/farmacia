package com.generation.farmacia.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.farmacia.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long>{
	
	public List<Produto> findAllByNomeContainingIgnoringCase (String nome);
	
	public List<Produto> findByPrecoBetween(BigDecimal preco1, BigDecimal preco2);
	
	public List<Produto> findAllByNomeContainingIgnoringCaseAndLaboratorioContainingIgnoringCase (String nome, String laboratorio);

}
