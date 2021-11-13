package core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.entity.Block;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.NonNull;
import lombok.val;

public class BlockchainService<T> {

    private final List<Block<T>> chain = new ArrayList<>();
    private static final Gson gson = new Gson();
    private static final Gson gsonPretty = new GsonBuilder().setPrettyPrinting()
        .create();

    /** First block in chain (descondier the genesis block) */
    private static final Integer INITIAL_INDEX = 1;
    private static final Integer DEFAULT_DIFFICULTY = 2;

    private final Integer difficulty;

    public BlockchainService(@NonNull T genesisData) {
        this.difficulty = DEFAULT_DIFFICULTY;
        chain.add(createGenesisBlock(genesisData));
    }

    public BlockchainService(@NonNull T genesisData, Integer difficulty) {
        this.difficulty = difficulty;
        chain.add(createGenesisBlock(genesisData));
    }

    private Block<T> createGenesisBlock(T genesisData) {
        return Block.<T>builder()
            .data(genesisData)
            .timestamp(new Date())
            .build();
    }

    public Block<T> getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public boolean chainValid() {

        for (int i = INITIAL_INDEX; i < this.chain.size(); i++) {

            val currentBlock = this.chain.get(i);
            val previousBlock = this.chain.get(i - 1);

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            if (!currentBlock.getHash().equals(currentBlock.generateHash())) {
                return false;
            }
        }

        return true;
    }

    public synchronized void addBlock(T data) {
        val block = Block.<T>builder()
            .previousHash(getLatestBlock().getHash())
            .timestamp(new Date())
            .data(data)
            .build();

        chain.add(block.mineBlock(difficulty));
    }

    @Override
    public String toString() {
        return gson.toJson(chain);
    }

    public void printPretty() {
        System.out.println(gsonPretty.toJson(chain));
    }
}
