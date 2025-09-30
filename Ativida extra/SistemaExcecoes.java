import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String msg) {
        super(msg);
    }
}

class SenhaInvalidaException extends Exception {
    public SenhaInvalidaException(String msg) {
        super(msg);
    }
}

class TransferenciaInvalidaException extends Exception {
    public TransferenciaInvalidaException(String msg) {
        super(msg);
    }
}

class DivisaoInteiraInvalidaException extends Exception {
    public DivisaoInteiraInvalidaException(String msg) {
        super(msg);
    }
}

class Calculadora {
    public static double dividir(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Erro: Divisão por zero não permitida.");
        }
        return a / b;
    }
}

class ConversorTemperatura {
    public static double converterCelsiusParaFahrenheit(double temperaturaCelsius) {
        if (temperaturaCelsius < -273.15) {
            throw new IllegalArgumentException("Erro: Temperatura abaixo do zero absoluto!");
        }
        return (temperaturaCelsius * 9 / 5) + 32;
    }
}

class Idade {
    public static void verificarIdade(int idade) {
        if (idade < 0 || idade > 150) {
            throw new IllegalArgumentException("Erro: Idade inválida!");
        }
    }
}

class ValidadorSenha {
    public static void validarSenha(String senha) throws SenhaInvalidaException {
        if (senha.length() < 6) {
            throw new SenhaInvalidaException("Erro: A senha deve ter no mínimo 6 caracteres.");
        }
    }
}

class ContaBancaria {
    private double saldo;

    public ContaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Erro: Saldo insuficiente para saque!");
        }
        saldo -= valor;
        System.out.println("Saque realizado. Saldo atual: R$" + saldo);
    }

    public void transferir(double valor, ContaBancaria destino) throws TransferenciaInvalidaException {
        if (valor <= 0) {
            throw new TransferenciaInvalidaException("Erro: Valor de transferência inválido!");
        }
        if (valor > saldo) {
            throw new TransferenciaInvalidaException("Erro: Saldo insuficiente para transferência!");
        }
        saldo -= valor;
        destino.saldo += valor;
        System.out.println("Transferência realizada. Saldo atual: R$" + saldo);
    }
}

class DivisaoInteira {
    public static int dividir(int a, int b) throws DivisaoInteiraInvalidaException {
        if (b == 0) throw new ArithmeticException("Divisão por zero não permitida.");
        if (a % b != 0) {
            throw new DivisaoInteiraInvalidaException("Erro: A divisão não é exata.");
        }
        return a / b;
    }
}

public class SistemaExcecoes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Divisão: 10 / 2 = " + Calculadora.dividir(10, 2));
            System.out.println("Divisão: 5 / 0 = " + Calculadora.dividir(5, 0));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("25°C = " + ConversorTemperatura.converterCelsiusParaFahrenheit(25) + "°F");
            System.out.println("-300°C = " + ConversorTemperatura.converterCelsiusParaFahrenheit(-300));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Idade.verificarIdade(25);
            System.out.println("Idade 25 válida!");
            Idade.verificarIdade(200);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            int numero = 10;
            int resultado = numero / 0;
            System.out.println(resultado);
        } catch (ArithmeticException e) {
            System.out.println("Erro: Não é possível dividir por zero.");
        }

        try {
            File file = new File("inexistente.txt");
            Scanner leitor = new Scanner(file);
            if (leitor.hasNextLine()) {
                System.out.println("Conteúdo do arquivo: " + leitor.nextLine());
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo não encontrado.");
        }

        try {
            String valor = "abc";
            int numeroConvertido = Integer.parseInt(valor);
            System.out.println("Número convertido: " + numeroConvertido);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor informado não é um número válido.");
        }

        ContaBancaria conta1 = new ContaBancaria(100);
        try {
            conta1.sacar(200);
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        try {
            ValidadorSenha.validarSenha("123");
        } catch (SenhaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        ContaBancaria conta2 = new ContaBancaria(50);
        try {
            conta1.transferir(-10, conta2);
        } catch (TransferenciaInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("10 / 2 = " + DivisaoInteira.dividir(10, 2));
        } catch (DivisaoInteiraInvalidaException | ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("10 / 3 = " + DivisaoInteira.dividir(10, 3));
        } catch (DivisaoInteiraInvalidaException | ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}