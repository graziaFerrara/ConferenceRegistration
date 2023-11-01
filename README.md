# ConferenceRegistration

This repository stores a Java Client/Server application that uses RMI to facilitate conference registration. 
<ol>
    <li>
        RMI Server
        <br/>
        The RMI server provides services for conference registration. It allows participants to register for the conference, view information about registered participants, and cancel registrations.
        <ul>
            <li>
                The organization of the congress provides the speakers of the various sessions with an interface, through which to enroll in a session, and the possibility of viewing the programs of the various days of the congress, with the interventions of the various sessions.
            </li>
            <li>
                The Server maintains the programs of the 3 days of the congress on its residence node, each of which is stored in a data structure in which each row corresponds to a session (12 in all for each day). For each session the names of the speakers who have registered are recorded (maximum 5).
            </li>
        </ul>
    </li>
    <li>
        Client
        <br/>
        the client has a textual interface that allows users to view conference details and register to attend to a Session.
        <ul>
            <li>
            The Client forwards the requests to the Server appropriately, and for every possible operation they also provide for the management of any abnormal conditions (such as for example the request to register for a non-existent day and / or session or for which all of the intervention spaces are full).
            </li>
            <li>    
                The Client is implemented as a cyclic process which continues to make synchronous requests until all user needs are exhausted.
            </li>
        </ul>
    </li>
</ol>
