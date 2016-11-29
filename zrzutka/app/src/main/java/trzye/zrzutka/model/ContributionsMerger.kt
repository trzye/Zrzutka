package trzye.zrzutka.model

import trzye.zrzutka.model.entity.contribution.Contribution
import java.util.*

object ContributionsMerger {
    fun merge(contributionsToMerge: MutableList<Contribution>): Contribution {
        val stack = Stack<Contribution>().apply {
            addAll(contributionsToMerge)
        }
        val newContribution = stack.pop().doCopy()
        while(stack.isNotEmpty()){
            newContribution.merge(stack.pop().doCopy())
        }
        return newContribution
    }


    fun Contribution.merge(merged: Contribution) {
        this._contributors.forEach { contributor ->
            if(merged._contributors.find { it.friend.id == contributor.})
        }
    }
}

