package src.messages.chordMessages;

import src.CLI.Peer;
import src.chord.ChordInfo;
import src.messages.*;
import java.util.*;


public class FindPredecessor extends Message {

    public ChordInfo nodeToFindPredecessor;

    public FindPredecessor(String ipAdress, int port, ChordInfo sender, ChordInfo nodeToFindPredecessor) {
        super(ipAdress, port, sender);
        this.nodeToFindPredecessor = nodeToFindPredecessor;
    }

    @Override
    public void handle() {

        try{

            ChordInfo predecessor = Peer.chordNode.getPredecessor();
            if(predecessor == null){
                return;
            }
            Peer.log("Peer " + Peer.chordNode.getNodeInfo().toString() + "is the predecessor."  );
            PredecessorMessage message = new PredecessorMessage(getSender().getIp(), getSender().getPort(), Peer.chordNode.getNodeInfo(), predecessor, Peer.chordNode.getSuccessorList());
            MessageSender sender = new MessageSender(message);
            sender.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}