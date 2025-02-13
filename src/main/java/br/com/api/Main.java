package br.com.api;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import spark.Spark;
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {

    private static List<Contato> listaContatos = new ArrayList<>();
    public static void main(String[] args) {

        Spark.port(8080);

        //Lidando com o CORS
        Spark.options("/*", new Route() {
            @Override
            public Object handle(Request requisicaoHttp, Response respostaHttp) throws Exception {

                String accessControlRequestHeaders = requisicaoHttp.headers("Access-Control-Request-Headers");
                
                if (accessControlRequestHeaders != null) 
                    respostaHttp.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                
                String accessControlRequestMethod = requisicaoHttp.headers("Access-Control-Request-Method");

                if (accessControlRequestMethod != null) 
                    respostaHttp.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                
                return "OK";
            }
        });

        //Informando o Browser que é aceito os metodos HTTP OPTIONS, GET, POST, PUT, DELETE para qualquer endereço
        Spark.before(new spark.Filter() {
            @Override
            public void handle(Request requisicaoHttp, Response respostaHttp) throws Exception {
                respostaHttp.header("Access-Control-Allow-Origin", "*"); // Permite todas as origens
                respostaHttp.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
            }
        });

        Spark.get("/contato/listar", listarContatos());
        Spark.get("/contato/listar/cpf/:cpf", listarPorCpf());
        Spark.get("/contato/listar/idade/:idade", listarPorIdade());
        Spark.get("/contato/listar/nome/:nome", listarPorNome());

        Spark.post("/contato/cadastrar", incluirContato());
        Spark.put("/contato/atualizar/:codigo", atualizarContato());
        Spark.delete("/contato/deletar/:codigo", excluirContato());

    }

    //Endpoints Principais

    private static Route atualizarContato() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                int id = Integer.parseInt(request.params(":codigo"));

                if (listaContatos.isEmpty()) {
                    response.status(404); 
                    return new Gson().toJson("Não existem contatos na base.");

                } else {
                    for(Contato pessoa : listaContatos){
                        if (pessoa.getId()  == id) {

                            // Converte o JSON recebido no corpo da requisição para um objeto Contato
                            Contato contatoAtualizado = new Gson().fromJson(request.body(), Contato.class);

                            // Atualiza os dados do contato

                            pessoa.setCpf(contatoAtualizado.getCpf());
                            pessoa.setNome(contatoAtualizado.getNome());
                            pessoa.setIdade(contatoAtualizado.getIdade());
                           
                            response.type("application/json");

                            response.status(200);

                            return new Gson().toJson("Usuário com ID " + id + " foi atualizado com sucesso!");

                        }
                       
                    }

                    response.status(404); // 404 Not Found
                    return new Gson().toJson("Contato com o ID " + id + " especificado não encontrado.");
                }

            }
        };
    }

    private static Route excluirContato() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                int id = Integer.parseInt(request.params(":codigo"));
             
                if (listaContatos.isEmpty()) {
                    response.status(404); // 404 Not Found
                    return new Gson().toJson("Não existem contatos na base");
                } 

                boolean contatoRemovido = listaContatos.removeIf(pessoa ->
                pessoa.getId() == id);

                if (contatoRemovido) {
                    response.type("application/json"); // Define o tipo de conteúdo como JSON

                    response.status(200);
                    return new Gson().toJson("Usuário com ID " + id + " foi excluído com sucesso!");
                } else {
                    response.status(404);
                    return new Gson().toJson("Contato com ID " + id + " não encontrado.");
                }
            }

        };

    }

    private static Route incluirContato() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");
                Contato user = new Gson().fromJson(request.body(), Contato.class);
                
                user.setId(listaContatos.size() + 1);

                listaContatos.add(user);

                response.status(201);

                return new Gson()
                .toJson(user);

            }

        };

    }

    private static Route listarContatos() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                response.type("application/json");
                List<Contato> contatos = new ArrayList<>();
                for (Contato pessoa : listaContatos) {
                    contatos.add(pessoa);
                }
                response.status(200);
                return new Gson()
                .toJson(contatos);
            }
        };
    }

    //Endpoints Extra 
    

    private static Route listarPorIdade() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response)  {
                try {

                    String idade = request.params(":idade");

                    List<Contato> contatosFiltrados = listaContatos.stream()
                    .filter(pessoa -> pessoa.getIdade().equals(idade))
                    .toList();

                    if (contatosFiltrados.isEmpty()) {
                        response.status(404); // 404 Not Found
                        return "Nenhum contato encontrado com a idade: " + idade;
                    }
        
                    StringBuilder resposta = new StringBuilder("\n--- Contatos com Idade: " + idade + " ---\n");
                    for (Contato pessoa : contatosFiltrados) {
                        resposta.append(pessoa.toString()).append("\n");
                    }
        
                    response.status(200); // 200 OK
                    return resposta.toString();


                    
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "Idade fornecida é inválida. Use um número inteiro.";
                }

            }

        };
    }

    private static Route listarPorCpf() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

               String cpf = request.params(":cpf");

               List<Contato> contatosFiltrados = listaContatos.stream()
                    .filter(pessoa -> pessoa.getCpf().equals(cpf))
                    .toList();

                if (contatosFiltrados.isEmpty()) {
                    response.status(404); // 404 Not Found
                    return "Nenhum contato encontrado com o CPF: " + cpf;
                }

                StringBuilder resposta = new StringBuilder("\n--- Contatos com CPF: " + cpf + " ---\n");
                for (Contato pessoa : contatosFiltrados) {
                    resposta.append(pessoa.toString()).append("\n");
                }
        
                response.status(200); // 200 OK
                return resposta.toString();
            }

        };
    }

    private static Route listarPorNome() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

               String nome = request.params(":nome").trim();

               List<Contato> contatosFiltrados = listaContatos.stream()
                    .filter(pessoa -> pessoa.getNome().equalsIgnoreCase(nome))
                    .toList();

                if (contatosFiltrados.isEmpty()) {
                    response.status(404); // 404 Not Found
                    return "Nenhum contato encontrado com o nome: " + nome;
                }

                StringBuilder resposta = new StringBuilder("\n--- Contatos com Nome: " + nome + " ---\n");
                for (Contato pessoa : contatosFiltrados) {
                    resposta.append(pessoa.toString()).append("\n");
                }
        
                response.status(200); // 200 OK
                return resposta.toString();
            }

        };
    }

}