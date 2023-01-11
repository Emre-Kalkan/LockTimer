package net.emrekalkan.locktimer.domain.usecase

interface UseCase<Input, Output> {
    suspend operator fun invoke(params: Input): Output
}

interface NoInputUseCase<Output> {
    suspend operator fun invoke(): Output
}