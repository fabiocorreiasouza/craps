// Exercício do livro "Java: como Programar 10ed"
// 6.33 (Modificação do jogo Craps)
// Preferi divergir do que o exercício pede
// Nome: Craps
// Simula o jogo de dados craps simplificadamente

import java.security.SecureRandom;
import java.util.Scanner;

public class Craps
{
	// cria um gerador seguro de números aleatórios para uso no método rollDice
	private static final SecureRandom randomNumbers = new SecureRandom();

	// tipo enum com constantes que representam o estado do jogo
	private enum Status { CONTINUE, WON, LOST, BUSTED };

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
		int bankBalance;
		int wager;
		int gain = 0;
		char answer;
		Scanner input = new Scanner(System.in);

		System.out.print("\n\nWELCOME TO CRAPS.\n\nEnter your bank balance (no cents): ");
		bankBalance = input.nextInt();
		input.nextLine(); // clean
		System.out.printf("Your bank balance is %d%n", bankBalance);

		while (true)
		{
			do
			{
				System.out.print("Please, sir, enter your wager (minor or equal your bank balance): ");
				wager = input.nextInt();
				input.nextLine(); // clean
			} while (wager > bankBalance);

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
					gameStatus = Status.LOST;
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

				if (sumOfDice == myPoint)
					gameStatus = Status.WON; // vitória por pontuação
				else if (sumOfDice == SEVEN)
					gameStatus = Status.LOST;
			}

			// exibe uma mensagem ganhou ou perdeu
			if (gameStatus == Status.WON)
			{
				System.out.println("Player wins!");
				gain += wager;
			}
			else
			{
				System.out.println("Player loses!");
				gain -= wager;
			}

			bankBalance += gain;

			if (bankBalance == 0)
			{
				gameStatus = Status.BUSTED;
				System.out.println(getSpeak(gameStatus, gain));
				break;
			}

			System.out.println(getSpeak(gameStatus, gain));

			System.out.print("Do you want to continue (yes or no)? ");
			answer = input.next().charAt(0);

			if (answer == 'y' || answer == 'Y')
				continue;
			else
			{
				System.out.println("Exiting...\nGood bye!");
				break; // exit CRAPS
			}
		}
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

	public static String getSpeak(Status gameStatus, int gain)
	{
		String phrase = null;

		if (gameStatus == Status.BUSTED) // você faliu
			phrase = "Sorry. You busted!";
		else if (gameStatus == Status.WON && gain > 0) // frases de situação positiva
			phrase = "You're up big. Now's the time to cash in your chips!";
		else if (gameStatus == Status.WON && gain <= 0 || gameStatus == Status.LOST && gain >= 0) // frases motivacionais
			phrase = "Aw c'mon, take a chance!";
		else if (gameStatus == Status.LOST) // frases de situação de cuidado
			phrase = "Oh, you're going for broke, huh?";

		return phrase;
	}
}
