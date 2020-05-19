package src.messages.chordMessages;

import src.messages.*;
import src.CLI.Peer;
import src.chord.ChordInfo;
import src.*;

public class NotifyMessage extends Message {

    public ChordInfo predecessor;

    public NotifyMessage(String ipAddress, int port, ChordInfo sender) {
        super(ipAddress, port, sender);
        this.predecessor = sender;
    }

    @Override
    public void handle() {
        try {
            if (Peer.chordNode.getPredecessor() == null || Utils.isBetween(Peer.chordNode.getPredecessor().getHashKey(),
                    Peer.chordNode.getNodeHash(), this.predecessor.getHashKey(), false)) {
                Peer.chordNode.setPredecessor(predecessor);
            } else {
                ChordInfo peerPred = Peer.chordNode.getPredecessor();
                Message message = new HandShakeMessage(peerPred.getIp(), peerPred.getPort(), Peer.chordNode.getNodeInfo());
                MessageSender sender = new MessageSender(message);
                if (!sender.send()) {
                    Peer.chordNode.setPredecessor(predecessor);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}