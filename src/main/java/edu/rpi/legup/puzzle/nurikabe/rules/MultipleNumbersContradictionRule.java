package edu.rpi.legup.puzzle.nurikabe.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.puzzle.nurikabe.NurikabeBoard;
import edu.rpi.legup.puzzle.nurikabe.NurikabeCell;
import edu.rpi.legup.puzzle.nurikabe.NurikabeType;
import edu.rpi.legup.puzzle.nurikabe.NurikabeUtilities;
import edu.rpi.legup.utility.DisjointSets;

import java.util.Set;

public class MultipleNumbersContradictionRule extends ContradictionRule {
    private final String NO_CONTRADICTION_MESSAGE = "Does not contain a contradiction at this index";
    private final String INVALID_USE_MESSAGE = "Contradiction must be a numbered cell";

    public MultipleNumbersContradictionRule() {
        super("Multiple Numbers",
                "All white regions cannot have more than one number.",
                "edu/rpi/legup/images/nurikabe/contradictions/MultipleNumber.png");
    }

    @Override
    public String checkContradictionAt(Board board, PuzzleElement puzzleElement) {
        NurikabeBoard nurikabeBoard = (NurikabeBoard) board;

        NurikabeCell cell = (NurikabeCell) nurikabeBoard.getPuzzleElement(puzzleElement);
        if(cell.getType() != NurikabeType.NUMBER) {
            return super.getInvalidUseOfRuleMessage() + ": " + INVALID_USE_MESSAGE;
        }
        DisjointSets<NurikabeCell> regions = NurikabeUtilities.getNurikabeRegions(nurikabeBoard);
        Set<NurikabeCell> numberedRegion = regions.getSet(cell);
        for (NurikabeCell c : numberedRegion) {
            if (c != cell && c.getType() == NurikabeType.NUMBER) {
                return null;
            }
        }
        return super.getNoContradictionMessage() + ": " + NO_CONTRADICTION_MESSAGE;
    }
}
