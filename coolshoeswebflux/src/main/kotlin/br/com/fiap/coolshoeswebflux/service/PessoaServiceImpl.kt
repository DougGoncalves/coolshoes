package br.com.fiap.coolshoeswebflux.service

import br.com.fiap.coolshoeswebflux.config.CloudConfigProperties
import br.com.fiap.coolshoeswebflux.dto.PessoaCreateDTO
import br.com.fiap.coolshoeswebflux.dto.PessoaDTO
import br.com.fiap.coolshoeswebflux.model.PessoaDocument
import br.com.fiap.coolshoeswebflux.repository.PessoaRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class PessoaServiceImpl(
        private val pessoaRepository: PessoaRepository,
        private val cloudConfigProperties: CloudConfigProperties
) : PessoaService {

    override fun create(pessoaCreateDTO: PessoaCreateDTO) =
            Mono.just(pessoaCreateDTO)
                    .map { PessoaDocument(nome = it.nome, cpf = it.cpf) }
                    .flatMap { pessoaRepository.save(it) }
                    .map { PessoaDTO(pessoaDocument = it) }

    override fun listAll() = pessoaRepository.findAll()
            .map { PessoaDTO(id = it?.id ?: "", nome = "${cloudConfigProperties.remoteFile} ${it.nome}" , cpf = it.cpf) }
            .delayElements(Duration.ofSeconds(1))

}