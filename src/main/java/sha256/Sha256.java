package sha256;


public class Sha256 {

    /**
     * Apply the SHA256 algorithm
     * @param input string used to apply the algorithm
     * @return hash corresponding to the input
     */
    public static String apply(String input) throws Exception {
        // Step 1 : transformation
        InputTransform inputTransform = new InputTransform(input);
        String transformed = inputTransform.transform();

        // Step 2 : create hash
        HashCreator hashCreator = new HashCreator(transformed);
        return hashCreator.getHashSha256();
    }

}