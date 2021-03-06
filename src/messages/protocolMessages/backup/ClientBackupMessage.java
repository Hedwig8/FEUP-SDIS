package src.messages.protocolMessages.backup;

import src.Utils;
import src.CLI.Peer;
import src.chord.ChordInfo;
import src.messages.Message;
import src.messages.MessageSender;


public class ClientBackupMessage extends Message {

    private byte[] content;
    private int repDegree;
    private int key;

    public ClientBackupMessage(String ipAddress, int port, ChordInfo sender, byte[] content, int repDegree, int key) {
        super(ipAddress, port, sender);
        this.content = content;
        this.repDegree = repDegree;
        this.key = key;
    }

    @Override
    public void handle() {

        //Se for o peer responsavel pela key
        if(Peer.chordNode.getPredecessor() != null && Utils.isBetween(Peer.chordNode.getPredecessor().getHashKey(), Peer.chordNode.getNodeHash(),key,false)){

            ChordInfo successor = Peer.chordNode.getSuccessor(0);
            Backup message = new Backup(successor.getIp(), successor.getPort(), Peer.chordNode.getNodeInfo(), getSender(), key, content, repDegree);
            MessageSender sender = new MessageSender(message);
            sender.send();
            Peer.forwarded.add(key);

        } else{
            ChordInfo n1 = Peer.chordNode.closestPrecedingNode(this.key);
            FindBackup message = new FindBackup(n1.getIp(), n1.getPort(), getSender(), this.key, this.content, this.repDegree);
            MessageSender sender = new MessageSender(message);
            sender.send();
        }
        
    }
    
}