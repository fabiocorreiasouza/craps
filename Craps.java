// Exercício modificado do livro "Java: Como programar"
// Nome: Craps
// Simula o jogo de dados craps simplificadamente

import java.security.SecureRandom;

public class Craps
{
	// cria um gerador seguro de números aleatórios para uso no método rollDice
	private static final SecureRandom randomNumbers = new SecureRandom();

	// tipo enum com constantes que representam o estado do jogo
	private enum Status { CONTINUE, WON, LOST };

	// constantes que representam lançamentos comuns dos dados
	private static final int SNAKE_EYES = 2;
	private static final int TREY = 3;
	private static final int SEVEN  = 7;
	private static final int YO_LEVEN = 11;
	private static final int BOX_CARS = 12;

	// joga uma partida de craps
	public static void main(String[] args)
	{
		int myPoint = 0; // pontos se não ganhar ou perder na 1ª rolagem
		Status gameStatus; // pode conter CONTINUE, WON ou LOST

		int sumOfDice = rollDice(); // primeira rolagem dos dados

		// determina o estado do jogo e a pontuação com base no primeiro lançamento
		switch (sumOfDice)
		{
			case SEVEN: // ganha com 7 no primeiro lançamento
			case YO_LEVEN: // ganha com 11 no primeiro lançamento
				gameStatus = Status.WON;
				break;
			case SNAKE_EYES: // perde com 2 no primeiro lançamento
			case TREY: // perde com 3 no primeiro lançamento
			case BOX_CARS: // perde com 12 no primeiro lançamento
				gameStatus.LOST;
				break;
			default: // não ganhou nem perdeu, portanto registra a pontuação
				gameStatus = Status.CONTINUE; // jogo não terminou
				myPoint = sumOfDice; // informa a pontuação
				System.out.printf("Point is %d%n", myPoint);
				break;
		}

		// enquanto o jogo não estiver completo
		while (gameStatus == Status.CONTINUE) // nem WON nem LOST
		{
			sumOfDice = rollDice(); // lança os dados novamente

			if (sumOfDice = myPoint)
				gameStatus = Status.WON; // vitória por pontuação
			else if (sumOfDice == SEVEN)
				gameStatus == Status.LOST;
		}

		// exibe uma mensagem ganhou ou perdeu
		if (gameStatus == Status.WON)
			System.out.println("Player wins!");
		else
			System.out.println("Plaer loses!");
	}

	// lança os dados, calcula a soma e exibe os resultados
	public static int rollDice()
	{
		// seleciona valores aleatórios do dado
		int dice1 = 1 + randomNumbers.nextInt(6);
		int dice2 = 1 + randomNumbers.nextInt(6);

		int sum = dice1 + dice2;

		System.out.printf("Player rolled %d + %d = %d%n",
			dice1, dice2, sum);

		return sum;
	}
}
