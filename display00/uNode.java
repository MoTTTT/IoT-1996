/************************************************************************/
/*	uNode Network Back End 						*/
/*	M.J.Colley '96							*/
/************************************************************************/

import java.net.*;
import java.io.*;

class uNode
{
	DataInputStream		is=	null;
	DataOutputStream	os=	null;
	int			state;
	int			access=	0;
	private Frame		rx=	new	Frame(); 
	private Frame		tx=	new	Frame();
	public	uNode	( DataInputStream is, DataOutputStream os )
	{
		this.is=	is;
		this.os=	os;
		state=		1;
	}
	void	processInput( )
	{
		try
		{
			access++;
			serverd.daccess++;
			rx.Address=	is.readInt();
			rx.Command=	is.readInt();
			switch	( rx.Command )
			{
			case 0:		/* Undefined		*/
				break;
			case 1:		/* Ping			*/
				os.writeInt	( rx.Address );
				os.writeInt	( 1 );
				os.flush	( ); 
				break;	
			case 2:		/* Access count request	*/
				os.writeInt	( rx.Address );
				os.writeInt	( 2 );
				os.writeInt	( serverd.daccess );
				os.flush	( );
				break;
			case 999:	/* Close Connection	*/
				state=	0;
				break;
			default:
				os.writeInt	( 0 );	
				os.flush	( );
			}
			os.flush();	
		}
		catch	(EOFException e)
		{
			state=	0;
			e.printStackTrace();
		} 
		catch	(IOException e)
		{
			e.printStackTrace();
	        }
	}
	boolean	Finished ( )
	{
		if	( state == 0 )
			return 	true;
		return	false;
	}
}

