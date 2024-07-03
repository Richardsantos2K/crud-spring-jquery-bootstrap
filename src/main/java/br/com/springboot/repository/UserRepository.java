package br.com.springboot.repository;

import br.com.springboot.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query(value = "select u from UserModel u where upper(trim(u.nome)) like %?1%")
    List<UserModel> searchforname (String nomes);
}
