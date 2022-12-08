package precourse;

import java.util.Random;
import java.util.HashSet;
import java.util.Scanner;

public class NumberBaseball {
    private String target;

    public String getTarget() {
        return this.target;
    }
    public void setTarget() {
        this.target = this.makeRandomThreeNumber();
    }

    public String makeRandomThreeNumber() {
        String number = "";
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        while (number.length() < 3) {
            int numInt = rand.nextInt(9);
            String numStr = Integer.toString(numInt);
            number += this.checkValidNumber(numStr, number);
        }
        return number;
    }

    public String checkValidNumber(String num, String tmp) {
        if (num.equals("0") || tmp.contains(num)) {
            return "";
        }
        return num;
    }

    public boolean examine(String ans) {
        int[] result = {0, 0}; // 0 : strike, 1 :  ball
        for (int i = 0; i < 3; i++) {
            this.examineUnit(i, ans.substring(i, i+1), result);
        }
        printResult(result);
        if (result[0] == 3) {
            return true;
        }
        return false;
    }

    public void examineUnit(int i, String num, int[] result) {
        if (this.target.indexOf(num) == i) {
            result[0]++;
            return;
        }
        if (this.target.contains(num)) {
            result[1]++;
        }
    }

    public void printResult(int[] result) {
        if (result[0] == 0 && result[1] == 0) {
            System.out.println("낫싱");
            return;
        }
        if (result[1] == 0) {
            System.out.println(String.format("%d 스트라이크", result[0]));
            return;
        }
        if (result[0] == 0) {
            System.out.println(String.format("%d 볼", result[1]));
            return;
        }
        System.out.println(String.format("%d 스트라이크 %d 볼", result[0], result[1]));
    }

    public String input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("숫자를 입력해주세요 : ");
        String s = scanner.next();
        while (!this.checkInput(s)) {
            System.out.println("잘못된 입력입니다. 1에서 9까지의 서로 다른 세개의 숫자를 입력해주세요.");
            System.out.print("숫자를 입력해주세요 : ");
            s = scanner.next();
        }
        return s;
    }

    public boolean checkInput(String input) {
        HashSet<Character> set = new HashSet<>();
        for (char c : input.toCharArray()) {
            set.add(c);
        }
        return !(input.length() != 3 || input.contains("0") || set.size() != 3);
    }

    public boolean continueGame() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt() == 1;
    }

    public void newGame() {
        this.setTarget();
        boolean isOver = false;
        while (!isOver) {
            String input = this.input();
            isOver = this.examine(input);
        }
    }

    public static void main(String[] args) {
        NumberBaseball nb = new NumberBaseball();
        boolean doing = true;
        while (doing) {
            nb.newGame();
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            doing = nb.continueGame();
        }
        System.out.println("게임을 종료합니다.");
    }
}
