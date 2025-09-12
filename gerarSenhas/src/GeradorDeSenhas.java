import org.apache.commons.lang3.RandomStringUtils;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Um programa para gerar senhas seguras utilizando a biblioteca Apache Commons Lang3.
 * O programa solicita ao usuário o comprimento desejado para a senha.
 * A senha gerada conterá uma mistura de letras maiúsculas, minúsculas, números e símbolos.
 */
public class GeradorDeSenhas {

    // Define os conjuntos de caracteres que serão utilizados na senha
    private static final String LETRAS_MAIUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWYXZ";
    private static final String LETRAS_MINUSCULAS = "abcdefghijklmnopqrstuvwyxz";
    private static final String NUMEROS = "0123456789";
    private static final String SIMBOLOS_ESPECIAIS = "!@#$%^&*()-_=+<>?";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("    GERADOR DE SENHAS SEGURAS EM JAVA   ");
        System.out.println("========================================");
        System.out.print("\nDigite o comprimento desejado para a senha (ex: 12): ");

        try {
            int comprimento = scanner.nextInt();

            if (comprimento < 8) {
                System.out.println("\nAviso: Senhas com menos de 8 caracteres são consideradas fracas.");
                System.out.print("Deseja continuar mesmo assim? (s/n): ");
                String confirmacao = scanner.next();
                if (!confirmacao.equalsIgnoreCase("s")) {
                    System.out.println("Operação cancelada.");
                    return;
                }
            }

            String senhaGerada = gerarSenhaSegura(comprimento);
            System.out.println("\nSenha gerada com sucesso!");
            System.out.println("Sua nova senha é: " + senhaGerada);

        } catch (java.util.InputMismatchException e) {
            System.err.println("\nErro: Entrada inválida. Por favor, insira um número inteiro.");
        } finally {
            scanner.close();
            System.out.println("\n========================================");
        }
    }

    /**
     * Gera uma senha segura com o comprimento especificado.
     * A senha é garantida para conter pelo menos um caractere de cada tipo:
     * maiúscula, minúscula, número e símbolo.
     *
     * @param comprimento O tamanho final da senha.
     * @return Uma String contendo a senha gerada.
     */
    public static String gerarSenhaSegura(int comprimento) {
        if (comprimento < 4) {
            throw new IllegalArgumentException("O comprimento da senha deve ser de pelo menos 4 caracteres para garantir a inclusão de todos os tipos de caracteres.");
        }

        // Gera um caractere aleatório de cada categoria para garantir a complexidade
        StringBuilder senhaInicial = new StringBuilder();
        senhaInicial.append(RandomStringUtils.random(1, LETRAS_MAIUSCULAS));
        senhaInicial.append(RandomStringUtils.random(1, LETRAS_MINUSCULAS));
        senhaInicial.append(RandomStringUtils.random(1, NUMEROS));
        senhaInicial.append(RandomStringUtils.random(1, SIMBOLOS_ESPECIAIS));

        // Combina todos os tipos de caracteres para o restante da senha
        String caracteresCombinados = LETRAS_MAIUSCULAS + LETRAS_MINUSCULAS + NUMEROS + SIMBOLOS_ESPECIAIS;

        // Gera o restante da senha
        int comprimentoRestante = comprimento - 4;
        senhaInicial.append(RandomStringUtils.random(comprimentoRestante, caracteresCombinados));

        // Embaralha os caracteres da senha para que os primeiros quatro não sejam sempre previsíveis
        List<Character> caracteres = senhaInicial.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(caracteres);

        // Constrói a senha final a partir da lista embaralhada
        StringBuilder senhaFinal = new StringBuilder(comprimento);
        for (Character character : caracteres) {
            senhaFinal.append(character);
        }

        return senhaFinal.toString();
    }
}