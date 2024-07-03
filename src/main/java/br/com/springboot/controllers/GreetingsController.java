package br.com.springboot.controllers;

import br.com.springboot.model.UserModel;
import br.com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

    @Autowired//injeção de dependencia
    private UserRepository userRepository;

    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {

        return "API test " + name + "!";
    }

    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String metodoHellouWorld(@PathVariable String nome){

        UserModel userModel = new UserModel();
        userModel.setNome(nome);
        userRepository.save(userModel);

        return "Hellou world: " + nome;
    }

    @GetMapping(value = "listatodos")
    @ResponseBody//retorna os dados para o corpo da resposta
    public ResponseEntity<List<UserModel>> listUser(){

        List<UserModel> users = userRepository.findAll();//executa consulta no database

        return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);//retorna lista json
    }


    @PostMapping(value = "salvartodos")//mapeia url
    @ResponseBody//descrição resposta
    public ResponseEntity<UserModel> saveUser(@RequestBody UserModel userModel){//recebe dados para save

        UserModel user = userRepository.save(userModel);

        return new ResponseEntity<UserModel>(user, HttpStatus.CREATED);

    }

    @PutMapping(value = "atualizar")//mapeia url
    @ResponseBody//descrição resposta
    public ResponseEntity<?> atualizar(@RequestBody UserModel userModel){//recebe dados para save

        if (userModel.getId() == null){
            return new ResponseEntity<String>("Id não informado", HttpStatus.OK);
        }

        UserModel user = userRepository.saveAndFlush(userModel);

        return new ResponseEntity<UserModel>(user, HttpStatus.OK);

    }

    @DeleteMapping(value = "delete")//mapeia url
    @ResponseBody//descrição resposta
    public ResponseEntity<String> deleteUser(@RequestParam Long iduser){//recebe dados para save

        userRepository.deleteById(iduser);

        return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

    }

    @GetMapping(value = "buscaruserid")//mapeia url
    @ResponseBody//descrição resposta
    public ResponseEntity<UserModel> buscaruserid(@RequestParam(name = "iduser") Long iduser){//recebe dados para save

        UserModel userModel = userRepository.findById(iduser).get();

        return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);

    }

    @GetMapping(value = "buscarpornome")
    @ResponseBody
    public ResponseEntity<List<UserModel>> buscarpornome(@RequestParam(name = "nome") String nome) {
        List<UserModel> listaUsuarios = userRepository.searchforname(nome.trim().toUpperCase());
        return new ResponseEntity<List<UserModel>>(listaUsuarios, HttpStatus.OK);
    }




}
