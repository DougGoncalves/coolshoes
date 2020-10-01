package br.com.fiap.coolshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.coolshoes.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findFirstByUsername(String username);
  
}
