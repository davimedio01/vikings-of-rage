import java.io.*;
import java.net.*;
import javax.swing.*;

public class Network {
     Socket socket = null;
     DataInputStream input = null;
     DataOutputStream output = null;
     
     VikingsOfRage game;
     boolean data = true;

     public Network(VikingsOfRage game, String IP, int port) {
          this.game = game;
          try {
               socket = new Socket(IP, port);
               output = new DataOutputStream(socket.getOutputStream());
               input = new DataInputStream(socket.getInputStream());
          } catch (UnknownHostException e) {
               JOptionPane.showMessageDialog(game, "Couldn't connect to server...\n" + e, "Error",
                         JOptionPane.ERROR_MESSAGE);
               System.exit(0);
          } catch (IOException e) {
               JOptionPane.showMessageDialog(game, "Can't exchange data with server...\n" + e, "Error",
                         JOptionPane.ERROR_MESSAGE);
               System.exit(0);
          }
     }

     public boolean alive() {
          return data;
     }

     public String readTypeMessage() {
          try {
               return input.readUTF();
          } catch (IOException e) {
               data = false;
               return "";
          }
     }

     public void sendMovement(String movement) {
          try {
               output.writeUTF("MOVEMENT");
               output.writeUTF(movement);
          } catch (IOException e) {
               data = false;
          }
     }

     public void sendAttack(Position player, Position enemy){
          try {
               output.writeUTF("ATTACK");
               output.writeInt(player.x);
               output.writeInt(player.y);
               output.writeInt(enemy.x);
               output.writeInt(enemy.y);
          } catch (IOException e) {
               data = false;
          }
     }

     public void readPosition(Position player, Position enemy) {
          try {
               player.x = input.readInt();
               player.y = input.readInt();
               enemy.x = input.readInt();
               enemy.y = input.readInt();
          } catch (IOException e) {
               data = false;
          }
     }

     public void readLife(PlayerLifes lifes){
          try{
               lifes.player1 = input.readInt();
               lifes.player2 = input.readInt();
          } catch(IOException e){
               data = false;
          }
     }

     public void flushOutput(){
          try{
               output.flush();
          } catch(IOException e){
               data = false;
          }
     }
}