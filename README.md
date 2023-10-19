# IoT-1996

Proof of concept for Internet of Things, done in 1996

This was a fun POC, which used the uNode with the potrack (array of variable resistors) attached to a uNode, in turn connected to a machine via RS232.

A java application on the machine communicated with the uNode to poll for changes to the potrack.

A listener on the java app, using the sun jeeves library (which I can now find no trace of, and java servlets superseded) to service http requests, returning potrack values.

A web page served a java applet (haven't heard of those for a while), which called the jeeves app to get a potrack value, and adjust a screen widget showing current setting.
