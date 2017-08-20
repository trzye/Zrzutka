package trzye.zrzutka.model.entity

fun <C, A> constructOrNull(argument: A?, construct: (argument: A) -> C): C?
        = if(argument != null) construct(argument) else null