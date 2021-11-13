package api;

import core.service.BlockchainService;

public class Main {

    private static final BlockchainService<String> blockchain = new BlockchainService<>("", 5);

    public static void main(String[] args) {
        blockchain.addBlock("asdas");
        blockchain.printPretty();
        blockchain.addBlock("testast");
        blockchain.printPretty();
        System.out.println(blockchain.chainValid());
    }
}
