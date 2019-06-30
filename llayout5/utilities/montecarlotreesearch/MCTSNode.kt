package llayout5.utilities.montecarlotreesearch

import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * An internal class used to represent nodes in a Monte Carlo Tree Search.
 * @param state The state associated to this Node.
 * @param parent The parent of this node, if there is one.
 * @see MCTSState
 * @since LLayout 5
 */
internal class MCTSNode(private val state: MCTSState, private val parent : MCTSNode? = null) {

    private companion object{
        /**
         * The minimal number of iterations that must be performed on a node
         * @since LLayout 5
         */
        private const val MINIMAL_ITERATION : Int = 2

        /**
         * A constant used to compute the score
         * @since LLayout 4
         */
        private const val SCORE_CONSTANT : Double = 10.0
    }

    /**
     * The number of iterations that have been run from this node or its children.
     * @since LLayout 5
     */
    private var iterations : Int = 0

    init{
        if(parent == null) iterations = 1
    }

    /**
     * The sum of the scores associated to this node and its children.
     * @since LLayout 5
     */
    private var score : Double = 0.0

    /**
     * True if the node has searched for next children, false otherwise.
     * @since LLayout 5
     */
    private var hasFoundNextChildren : Boolean = false

    /**
     * The set of the nodes that can follow from this one.
     * @since LLayout 5
     */
    private var nextNodes : MutableList<MCTSNode> = mutableListOf()

    /**
     * The mean score of this node.
     * @since LLayout 5
     */
    private fun meanScore() : Double =
            if(hasTooFewIterations() || parent == null)
                Double.MAX_VALUE
            else
                score / iterations + SCORE_CONSTANT * sqrt(2 * ln(parent.iterations.toDouble()) / iterations)

    /**
     * The [MCTSState] associated to this node.
     * @see MCTSState
     * @since LLayout 5
     */
    internal fun state() : MCTSState = state

    /**
     * True if the node doesn't have enough starting iterations.
     * @since LLayout 5
     */
    private fun hasTooFewIterations() : Boolean = iterations < MINIMAL_ITERATION

    /**
     * True if the node doesn't have any children.
     * @since LLayout 5
     */
    private fun hasNoChildren() : Boolean = nextNodes.size == 0

    /**
     * Computes this node's children.
     * @see MCTSState.findNextMCTSStates
     * @since LLayout 5
     */
    private fun generateNewStates(){
        for(newState : MCTSState in state.findNextMCTSStates()){
            nextNodes.add(MCTSNode(newState, this))
        }
        hasFoundNextChildren = true
    }

    /**
     * Simulates a random step of given depth.
     * @since LLayout 5
     */
    internal fun simulateRandomNextStep(depth : Int){
        if(!hasFoundNextChildren){
            generateNewStates()
        }
        val bestChild : MCTSNode? = bestIterableChild()
        if(bestChild == null){
            addScore(state.MCTSStateScore())
        }else{
            bestChild.addRandomScoreAfter(depth)
        }
    }

    /**
     * Simulates a random step to the end of the tree.
     * @since LLayout 5
     */
    internal fun simulateRandomNextStepToEnd(){
        if(!hasFoundNextChildren){
            generateNewStates()
        }
        val bestChild : MCTSNode? = bestIterableChild()
        if(bestChild == null){
            addScore(state.MCTSStateScore())
        }else{
            bestChild.addRandomScoreAtEnd()
        }
    }

    /**
     * Adds the given score to this node and its parent.
     * @since LLayout 5
     */
    private fun addScore(score : Double){
        iterations++
        this.score += score
        parent?.addScore(score)
    }

    /**
     * Returns the best child on which the algorithm can iterate, if there is one, or null otherwise.
     * @since LLayout 5
     */
    private fun bestIterableChild() : MCTSNode?{
        var best : MCTSNode? = null
        for(node : MCTSNode in nextNodes){
            if(best == null ||
                    ( node.hasTooFewIterations() && node.iterations < best.iterations) ||
                    ( stateIsNotLocked(node.state) && node.meanScore() > best.meanScore() ) ){
                best = node
            }
        }
        return best
    }

    /**
     * Returns the score of a state [depth] random moves after this node.
     * @since LLayout 5
     */
    private fun randomScoreAfter(depth : Int) : Double{
        var iterationState = state
        for(i : Int in 1..depth){
            if(stateIsNotLocked(iterationState)){
                iterationState = iterationState.generateRandomNextMCTSState()
            }
        }
        return iterationState.MCTSStateScore()
    }

    /**
     * Adds the score of a state [depth] random moves after this node to this node.
     * @since LLayout 5
     */
    private fun addRandomScoreAfter(depth : Int) = addScore(randomScoreAfter(depth))

    /**
     * Returns the score of a state randomly chosen at the end of this node's tree.
     * @since LLayout 5
     */
    private fun randomScoreAtEnd() : Double{
        var iterationState = state
        while(stateIsNotLocked(iterationState)){
            iterationState = iterationState.generateRandomNextMCTSState()
        }
        return iterationState.MCTSStateScore()
    }

    /**
     * Adds the score of a state randomly choosen at the end of this node's tree to this node.
     * @since LLAyout 5
     */
    private fun addRandomScoreAtEnd() = addScore(randomScoreAtEnd())

    /**
     * Returns the best of this node's children, i.e. the one with the greater score.
     * @since LLayout 5
     */
    internal fun bestChild() : MCTSNode{
        return if(hasNoChildren()){
            this
        }else{
            var bestChild : MCTSNode = nextNodes[Random.nextInt(nextNodes.size)]
            for(node : MCTSNode in nextNodes){
                if(node.meanScore() > bestChild.meanScore()) bestChild = node
            }
            bestChild
        }
    }

    /**
     * Returns true if the given state has children.
     * @since LLayout 5
     */
    private fun stateIsNotLocked(state : MCTSState) : Boolean = state.hasNextMCTSStates()

}