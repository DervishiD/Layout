package llayout4.utilities.montecarlotreesearch

internal class MCTSNode(private val state: MCTSState, private val parent : MCTSNode? = null) {

    private var iterations : Int = 0

    private var score : Double = 0.0

    private var hasFoundNextChildren : Boolean = false

    private var nextNodes : MutableList<MCTSNode> = mutableListOf()

    private var lockedNodes : MutableSet<MCTSNode> = mutableSetOf()

    private var locked : Boolean = false

    private fun meanScore() : Double = if(hasNoIterations()) 0.0 else score / iterations

    internal fun state() : MCTSState = state

    private fun hasNoIterations() : Boolean = iterations == 0

    private fun hasNoChildren() : Boolean = nextNodes.size == 0 && lockedNodes.size == 0

    private fun lock(){
        parent?.lockChild(this)
        locked = true
    }

    private fun lockChild(child : MCTSNode){
        nextNodes.remove(child)
        lockedNodes.add(child)
    }

    private fun generateNewStates(){
        for(newState : MCTSState in state.findNextMCTSStates()){
            nextNodes.add(MCTSNode(newState, this))
        }
        hasFoundNextChildren = true
    }

    internal fun simulateRandomNextStep(depth : Int){
        if(!hasFoundNextChildren){
            generateNewStates()
        }
        val bestChild : MCTSNode? = bestIterableChild()
        if(bestChild == null){
            //There are no more available children. The search must continue from above.
            lock()
            parent?.simulateRandomNextStep(depth)
        }else{
            addScore(bestChild.randomScoreAfter(depth))
        }
    }

    internal fun simulateRandomNextStepToEnd(){
        if(!hasFoundNextChildren){
            generateNewStates()
        }
        val bestChild : MCTSNode? = bestIterableChild()
        if(bestChild == null){
            //There are no more available children. The search must continue from above.
            lock()
            parent?.simulateRandomNextStepToEnd()
        }else{
            addScore(bestChild.randomScoreAtEnd())
        }
    }

    private fun addScore(score : Double){
        iterations++
        this.score += score
        parent?.addScore(score)
    }

    private fun bestIterableChild() : MCTSNode?{
        var best : MCTSNode? = null
        for(node : MCTSNode in nextNodes){
            if(best == null || ( stateIsNotLocked(node.state) && node.meanScore() > best.meanScore() ) ){
                best = node
            }
        }
        return best
    }

    private fun randomScoreAfter(depth : Int) : Double{
        var iterationState = state
        for(i : Int in 1..depth){
            if(stateIsNotLocked(iterationState)){
                iterationState = iterationState.generateRandomNextMCTSStates()
            }
        }
        return iterationState.MCTSStateScore()
    }

    private fun randomScoreAtEnd() : Double{
        var iterationState = state
        while(stateIsNotLocked(iterationState)){
            iterationState = iterationState.generateRandomNextMCTSStates()
        }
        return iterationState.MCTSStateScore()
    }

    internal fun bestChild() : MCTSNode{
        return if(hasNoChildren()){
            this
        }else{
            var bestChild : MCTSNode = if(nextNodes.size > 0) nextNodes[0] else lockedNodes.first()
            for(node : MCTSNode in nextNodes){
                if(node.meanScore() > bestChild.meanScore()) bestChild = node
            }
            for(node : MCTSNode in lockedNodes){
                if(node.meanScore() > bestChild.meanScore()) bestChild = node
            }
            bestChild
        }
    }

    private fun stateIsNotLocked(state : MCTSState) : Boolean = state.hasNextMCTSStates()

}