/************************************************************************/
/*	Control Front End for the uNode (pronounced microNode)		*/
/*	M.J.Colley	'96						*/
/************************************************************************/

import Frame;
import java.net.*;
import java.io.*;
import java.applet.Applet;
import java.awt.*;

public class uNodeClient extends Applet implements Runnable
{
	Socket			soc = null;
	DataOutputStream	os = null;
	DataInputStream		is = null;
	Label			statusl;
	TextField		accessf;
	int	variable01=	0;
	int	outCommand=	1;
	boolean	running=	false;
	Frame	rx=		new Frame( );
	Frame	tx=		new Frame( );
	Thread	pollThread;
	public	void	init	( )
	{
		accessf=	new	TextField();
		accessf.setEditable	( false);
		statusl=	new	Label();
		statusl.setAlignment	( Label.CENTER ); 
		setLayout	( new java.awt.GridLayout ( 0, 2) );
		add		( new Button("uNode Server Access Count:" ));
		add		( accessf );
		add		( new Button("Status:" ));
		add		( statusl);
		statusl.setText	( "Initialising..." );
		validate();
	}
        public	void	start	( )
        {
		try
		{
			statusl.setText	( "Connecting to uNode Server..." );
			repaint	( );
			soc=	new Socket		("beth", 5000);
			os=	new DataOutputStream	(soc.getOutputStream());
			is=	new DataInputStream	(soc.getInputStream());
			running=	true;
			statusl.setText	( "Connected. Cool." );
			repaint();
	        }
		catch	(UnknownHostException e)
		{
			statusl.setText	( "Unknown Host: Beth" );
			repaint( );
	        }
		catch	(IOException e)
		{
			statusl.setText	( "Couldn't get I/O for the connection to Beth" );
			repaint( );
		}
		if	(soc != null && os != null && is != null)
		{
			if	( pollThread== null)
				pollThread= new Thread(this);
			pollThread.start( );
		}
		else
		{
			statusl.setText	( "Could not connect." );
			repaint( );
		}
	}
	public	void	run	( )
	{
		if	( Thread.currentThread() != pollThread )
			return;
		try
		{
			while ( running )
  	     		{
				os.writeInt( 1 );
				os.writeInt( 2 );
  	 	 		os.flush();
				rx.Address=	is.readInt( );
				rx.Command=	is.readInt( );
				if	( rx.Command== 2 )
				{
					accessf.setText( String.valueOf( is.readInt()) );
					repaint	( ); 
 	      			}
				Thread.sleep	( 100 );
			}
			os.writeInt( 1 );
			os.writeInt( 999 );	/* Close Connection */
  	 	 	os.flush();
			os.close();
 	      		is.close();
			soc.close();
  	      	}
		catch	(UnknownHostException e)
		{
			statusl.setText	( "Unknown Host Exception: " + e );
			repaint	( ); 
		}
		catch	(IOException e)
		{
			statusl.setText	( "IO Exception:  " + e );
			repaint	( ); 
		}
		catch	(InterruptedException e)
		{
			statusl.setText	( "Interrupted Exception: " + e );
			repaint	( ); 
		}
	}
	public	void	stop	( )
	{
		running=	false;
		pollThread=	null;
	}
	public	boolean	mouseDown ( Event event, int x, int y )
	{
		if	( running )	stop( );
		else	 start( );
		return	true;
	}
}

