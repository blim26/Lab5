package poker.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import exceptions.DeckException;
import netgame.common.Hub;
import pokerBase.Action;
import pokerBase.Card;
import pokerBase.CardDraw;
import pokerBase.Deck;
import pokerBase.GamePlay;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Player;
import pokerBase.Rule;
import pokerBase.Table;
import pokerEnums.eAction;
import pokerEnums.eCardDestination;
import pokerEnums.eDrawCount;
import pokerEnums.eGame;
import pokerEnums.eGameState;

public class PokerHub extends Hub {

	private Table HubPokerTable = new Table();
	private GamePlay HubGamePlay;
	private int iDealNbr = 0;
	private eGameState eGameState;

	public PokerHub(int port) throws IOException {
		super(port);
	}

	protected void playerConnected(int playerID) {

		if (playerID == 2) {
			shutdownServerSocket();
		}
	}

	protected void playerDisconnected(int playerID) {
		shutDownHub();
	}

	protected void messageReceived(int ClientID, Object message) {

		if (message instanceof Action) {
			//TODO: If the Action = StartGame, start the game...
			//		Create an instance of GamePlay, set all the parameters
			
			if (((Action) message).getAction() == eAction.StartGame)
			{
				Rule rule = new Rule(((Action) message).geteGame());
				
				GamePlay gameplay = new GamePlay(rule, ((Action) message).getPlayer().getPlayerID());
			}

			//TODO: If Action = Sit or Leave, send the Table
			//		back to the client
			//TODO: If Action = Sit, add the player to the table
			else if (((Action) message).getAction() == eAction.Sit)
			{
				HubPokerTable.AddPlayerToTable(((Action) message).getPlayer()); 
				resetOutput();
				sendToAll(HubPokerTable);
			}

			//TODO: If Action = Sit or Leave, send the Table
			//		back to the client
			//TODO: If Action = Leave, remove the player from the table
			else if (((Action) message).getAction() == eAction.Leave)
			{
				Table.StateOfTable(HubPokerTable);
				HubPokerTable.RemovePlayerFromTable(((Action) message).getPlayer());
				Table.StateOfTable(HubPokerTable);
				resetOutput();
				sendToAll(HubPokerTable);
				
			}
			//TODO: If Action = GameState, send HubGamePlay 
			//		back to the client
			else if (((Action) message).getAction() == eAction.GameState)
			{
				sendToAll(HubGamePlay);
			}
			
			

			
			
			
			
			
		}

		System.out.println("Message Received by Hub");
		
		sendToAll("Sending Message Back to Client");
	}

}
