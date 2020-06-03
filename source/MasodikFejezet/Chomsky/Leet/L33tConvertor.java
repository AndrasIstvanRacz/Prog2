import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class L33tConvertor {

    private String toLeetCode(String str) {
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        StringBuilder result = new StringBuilder();
        HashMap<Character, String> map = new HashMap<Character, String>();
        map.put('A', "@");
        map.put('B', "ß");
        map.put('C', "©");
        map.put('D', "đ");
        map.put('E', "€");
        map.put('F', "ƒ");
        map.put('G', "6");
        map.put('H', "#");
        map.put('I', "!");
        map.put('J', "¿");
        map.put('K', "X");
        map.put('L', "£");
        map.put('M', "M");
        map.put('N', "r");
        map.put('O', "0");
        map.put('P', "p");
        map.put('Q', "0");
        map.put('R', "®");
        map.put('S', "$");
        map.put('T', "7");
        map.put('U', "µ");
        map.put('V', "v");
        map.put('W', "w");
        map.put('X', "%");
        map.put('Y', "¥");
        map.put('Z', "z");
        
        for (int i = 0; i < str.length(); i++) {
            char key = Character.toUpperCase(str.charAt(i));
            Matcher matcher = pattern.matcher(Character.toString(key));
            if (matcher.find()) {
                result.append(key);
                result.append(' ');
            } else {
                result.append(map.get(key));
            }
        }
        return result.toString();      
    }

    public static void main(String[] args) throws IOException {
        L33tConvertor obj = new L33tConvertor();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String leetWord;
        int cont;
        do {

                    System.out.println("\nEnter the English Words :-");
                    leetWord = br.readLine();
                    String leet = obj.toLeetCode(leetWord);
                    System.out.println("The 1337 Code is :- " + leet);

            System.out.println("\n\nDo you want to continue ? [1=Yes and 0=No]");
            cont = Integer.parseInt(br.readLine());
        } while (cont != 0);
    }
}