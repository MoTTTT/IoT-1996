/* Generic Server Daemon Class						*/
/* M.J.Colley '96							*/
/* Listens on a port and spawns threads to handle sockets		*/

import java.net.*;
import java.io.*;

class	serverd
{
	public	static	int	daccess=	0;
	public	static	void	main	(String[] args)
	{
		ServerSocket	serverSocket=	null;
		boolean		listening=	true;
		int		port=		5000;
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch	(IOException e)
		{
			System.err.println("Could not listen on port: "
				+ port + ", " + e.getMessage());
			System.exit(1);
		}
		while	(listening)
		{
			Socket clientSocket = null;
			try
			{
				clientSocket = serverSocket.accept();
			}
			catch	(IOException e)
			{
				System.err.println("Accept failed: "
					+ port + ", " + e.getMessage());
				continue;
			}
			new	servert	(clientSocket).start();
		}
		try
		{
			serverSocket.close();
		}
		catch (IOException e)
		{
			System.err.println("Could not close server socket."
				+ e.getMessage());
		}
	}
}










