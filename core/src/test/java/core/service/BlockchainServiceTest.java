package core.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockchainServiceTest {

    private BlockchainService<Object> blockchain;

    @BeforeEach
    private void init() {
        blockchain = new BlockchainService<>("");
    }

    @Test
    void chainIsValidOnlyGenesisTest() {
        assertTrue(blockchain.chainValid());
    }

    @Test
    void chainIsValidTest() {
        blockchain.addBlock("asdasdasda");
        blockchain.addBlock(3);
        assertTrue(blockchain.chainValid());
    }

    @Test
    void printPrettyTest() {
        blockchain.addBlock("asdasdasda");
        blockchain.addBlock(3);
        blockchain.printPretty();
    }

}
