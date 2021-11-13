package core.entity;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.val;

@Getter
@ToString
public class Block<T> {

    @NonNull private final T data;
    @NonNull private final Date timestamp;

    private final String hash;
    private final String previousHash;
    private Integer nonce = 0;

    @Builder
    public Block(@NonNull T data, @NonNull Date timestamp, String previousHash) {
        this.data = data;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.hash = generateHash();
    }

    private Block(@NonNull T data, @NonNull Date timestamp, String hash, String previousHash, Integer nonce) {
        this.data = data;
        this.timestamp = timestamp;
        this.hash = hash;
        this.previousHash = previousHash;
        this.nonce = nonce;
    }

    public String generateHash() {
        return Hashing.sha256()
            .hashString(this.data + this.timestamp.toString() + this.previousHash + this.nonce, StandardCharsets.UTF_8)
            .toString();
    }

    private String getDifficultChars(@NonNull Integer difficulty) {
        return Arrays.stream(IntStream.generate(() -> 0).limit(difficulty).toArray())
            .mapToObj(Integer::toString)
            .reduce("", (left, right) -> left + right);
    }

    public Block<T> mineBlock(@NonNull Integer difficulty) {
        String hash = this.hash;

        while (!hash.substring(0, difficulty).equals(getDifficultChars(difficulty))) {
            this.nonce++;
            hash = generateHash();
        }

        return new Block<>(this.data, this.timestamp, hash, this.previousHash, this.nonce);
    }
}
