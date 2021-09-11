import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class App {
     private static String texto = "";

    public static void main(String[] args) throws Exception {
        String path = "";
        String nomeArquivo = "";
        try {
            // Entrar Diretório Arquivo
            System.out.println("Digite o diretório do arquivo a ser validado: ");
            path = System.console().readLine();

            // Escanear Arquivo XML
            File arquivo = new File(path);
            Scanner scanner = new Scanner(arquivo);
            texto = validarLinhas(scanner);
            nomeArquivo = arquivo.getName();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro na leitura do arquivo!");
            e.printStackTrace();
        }
        
        try {
            // Nomear Aquivo Validado
            path =  path.replaceAll(nomeArquivo, "");
            String novoNomeArquivo = "ArquivoValidado_" + nomeArquivo;
            String novoPath = path + novoNomeArquivo;
            // Criar do Arquivo Validado
            File file = new File(novoPath);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(novoPath);
            
            fileWriter.write(texto);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo!");
            e.printStackTrace();
        }
    }

    public static String validarLinhas(Scanner scanner) {

        // Validação linha a linha
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            linha.trim();
            String resultado = "";

            if (!linha.equals("")) {

                if (resultado.equals("")) {
                    Stack<Character> stack = new Stack<Character>(); 

                    for (Character value : linha.toCharArray()) {
                        switch (value) {
                            case '[':
                                stack.push(value);
                                break;
                            case '{':
                                stack.push(value);
                                break;
                            case '(':
                                stack.push(value);
                                break;
                            case ')':
                                if (!stack.empty() && stack.peek().equals('(')) {
                                    stack.pop();
                                } else {
                                    resultado = "Inválido";
                                }
                                break;
                            case ']':
                                if (!stack.empty() && stack.peek().equals('[')) {
                                    stack.pop();
                                } else {
                                    resultado = "Inválido";
                                }
                                break;
                            case '}':
                                if (!stack.empty() && stack.peek().equals('{')) {
                                    stack.pop();
                                } else {
                                    resultado = "Inválido";
                                }
                                break;
                            default:
                                break;
                        }
                    }
    
                    if (resultado.equals("")) {
                        resultado = "Válido";
                    }
                }
                texto = texto + linha + " - " + resultado + "\n";
            }
        }
        return texto;
    }
}
