package src.messages;

import src.CLI.Peer;
import src.chord.ChordInfo;

public class SuccessorMessage extends Message {

    private ChordInfo successorInfo;

    public SuccessorMessage(String ipAddressDest, int portDest, ChordInfo sender, ChordInfo successorInfo) {
        super(ipAddressDest, portDest, sender);
        this.successorInfo = successorInfo;
    }

    @Override
    public void handle() {
        Peer.log("Sucessor was set to: " + successorInfo.toString());
        Peer.chordNode.setFinger(successorInfo, 0);
        NotifyMessage message = new NotifyMessage(successorInfo.getIp(), successorInfo.getPort(),
                    Peer.chordNode.getNodeInfo());
        MessageSender sender = new MessageSender(message);
        sender.send();
    }
    
}