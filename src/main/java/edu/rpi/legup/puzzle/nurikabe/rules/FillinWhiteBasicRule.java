package edu.rpi.legup.puzzle.nurikabe.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.BasicRule;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.nurikabe.NurikabeBoard;
import edu.rpi.legup.puzzle.nurikabe.NurikabeCell;
import edu.rpi.legup.puzzle.nurikabe.NurikabeType;

public class FillinWhiteBasicRule extends BasicRule {

    public FillinWhiteBasicRule() {
        super("Fill In White",
                "If there an unknown region surrounded by white, it must be white.",
                "edu/rpi/legup/images/nurikabe/rules/FillInWhite.png");
    }

    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        NurikabeBoard board = (NurikabeBoard) transition.getBoard();
        NurikabeBoard origBoard = (NurikabeBoard) transition.getParents().get(0).getBoard();
        ContradictionRule contraRule = new IsolateBlackContradictionRule();

        NurikabeCell cell = (NurikabeCell) board.getPuzzleElement(puzzleElement);

        if (cell.getType() != NurikabeType.WHITE) {
            return "Only white cells are allowed for this rule!";
        }
        NurikabeBoard modified = (NurikabeBoard) origBoard.copy();
        modified.getPuzzleElement(puzzleElement).setData(NurikabeType.BLACK.toValue());
        if (contraRule.checkContradictionAt(modified, puzzleElement) != null) {
            return "white cells must be placed in a region of white cells!";
        }
        return null;
    }

    @Override
    public Board getDefaultBoard(TreeNode node) {
        return null;
    }
}
