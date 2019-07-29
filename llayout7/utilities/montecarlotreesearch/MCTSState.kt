package llayout7.utilities.montecarlotreesearch

/**
 * This interface is implemented by objects that can perform a Monte Carlo Tree Search to find the best next state.
 * @since LLayout 5
 */
interface MCTSState {

    companion object{

        /**
         * Finds the best next state according to a Monte Carlo Tree Search from the given state.
         * The algorithm will perform [numberOfIterations] iterations, and each iteration will randomly find a state [depthOfIteration]
         * states later.
         * Think of a game of chess. Maybe you want the algorithm to try [numberOfIterations] = 50000 iterations of thinking
         * [depthOfIteration] = 6 moves ahead starting from the position [state].
         * @return The best next state according to the algorithm.
         * 2since LLayout 5
         */
        fun computeNextState(state : MCTSState, numberOfIterations : Int, depthOfIteration : Int) : MCTSState{
            if(numberOfIterations < 0) throw IllegalArgumentException("The number of iterations, $numberOfIterations, must be positive.")
            if(depthOfIteration == 0) return state
            if(depthOfIteration < 0) return computeToBottom(state, numberOfIterations)

            val rootNode = MCTSNode(state)

            for(i : Int in 1..numberOfIterations){
                rootNode.simulateRandomNextStep(depthOfIteration)
            }

            return rootNode.bestChild().state()
        }

        /**
         * Finds the best next state according to a Monte Carlo Tree Search from the given state.
         * The algorithm will perform [numberOfIterations] iterations, and each iteration will randomly generate states until it can't find more.
         * Think of a game of tic-tac-toe. Maybe you want to try [numberOfIterations] = 200 iterations of randomly ticking the grid
         * until the end of the game, starting from the state [state].
         * @return The best next state according to the algorithm.
         * 2since LLayout 5
         */
        fun computeToBottom(state : MCTSState, numberOfIterations: Int) : MCTSState{
            if(numberOfIterations < 0) throw IllegalArgumentException("The number of iterations, $numberOfIterations, must be positive.")

            val rootNode = MCTSNode(state)

            for(i : Int in 1..numberOfIterations){
                rootNode.simulateRandomNextStepToEnd()
            }

            return rootNode.bestChild().state()
        }

    }

    /**
     * Assigns a score to this [MCTSState]. Keep in mind that the algorithm defines the best next state as the one with the bigger score.
     * Most states should have a score closer to the tens than the hundreds, positive or negative, to suit the implementation better.
     * @see computeNextState
     * @see computeToBottom
     * @since LLayout 5
     */
    fun MCTSStateScore() : Double

    /**
     * Finds the states that can follow from this one.
     * Think about all the possibles moves the white can execute from a given position in chess.
     * @see computeNextState
     * @see computeToBottom
     * @since LLayout 5
     */
    fun findNextMCTSStates() : Collection<MCTSState>

    /**
     * Finds a random state that can follow from this one.
     * Think about a random move of the white at a given position in chess.
     * @see computeNextState
     * @see computeToBottom
     * @since LLayout 5
     */
    fun generateRandomNextMCTSState() : MCTSState

    /**
     * Determines whether or not any state can follow from this one.
     * Think about a position in chess. If the white can't move, there can be no next state.
     * @see computeNextState
     * @see computeToBottom
     * @since LLayout 5
     */
    fun hasNextMCTSStates() : Boolean

}