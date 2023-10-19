/* Generic Server Thread Class						*/
/* M.J.Colley '96							*/
/* Handles a server socket created by the Server Daemon			*/

import java.net.*;
import java.io.*;

class servert extends Thread
{
	Socket	socket=	null;
	servert	( Socket socket )
	{
		super("servert");
		this.socket = socket;
	}
	public void run()
	{
		try
		{
			DataInputStream is = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));
			DataOutputStream os = new DataOutputStream(
				new BufferedOutputStream(socket.getOutputStream()));
			uNode node00 = new uNode( is, os );
			while	( ! node00.Finished( ) )
			{
				node00.processInput( );
			}
			os.close();
			is.close();
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
	        }
	}
}




















