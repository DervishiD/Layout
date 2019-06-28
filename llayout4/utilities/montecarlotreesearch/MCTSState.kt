package llayout4.utilities.montecarlotreesearch

interface MCTSState {

    companion object{

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

        fun computeToBottom(state : MCTSState, numberOfIterations: Int) : MCTSState{
            if(numberOfIterations < 0) throw IllegalArgumentException("The number of iterations, $numberOfIterations, must be positive.")

            val rootNode = MCTSNode(state)

            for(i : Int in 1..numberOfIterations){
                rootNode.simulateRandomNextStepToEnd()
            }

            return rootNode.bestChild().state()
        }

    }

    fun MCTSStateScore() : Double

    fun findNextMCTSStates() : Collection<MCTSState>

    fun generateRandomNextMCTSStates() : MCTSState

    fun hasNextMCTSStates() : Boolean

}