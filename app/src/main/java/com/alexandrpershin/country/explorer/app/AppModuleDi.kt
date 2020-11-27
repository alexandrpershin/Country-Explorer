package com.alexandrpershin.country.explorer.app

import com.alexandrpershin.country.explorer.api.BackendApiFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule: Module = module {

    //Retrofit
    single<Retrofit> { BackendApiFactory().provideRetrofit(get(), ) }
//
//    //Local database
//    single<LocalDatabase> { LocalDatabase.getInstance(androidApplication()) }
//
//    //Repository
//    single<AuthRepository> { AuthRepositoryImpl(get()) }
//    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
//    single<StoryRepository> { StoryRepositoryImpl(get()) }
//    single<VocabularyRepository> { VocabularyRepositoryImpl(get()) }
//
//    //API service
//    single<AuthService> { createApiService<AuthService>(get()) }
//    single<ProfileService> { createApiService<ProfileService>(get()) }
//    single<StoryService> { createApiService<StoryService>(get()) }
//    single<VocabularyService> { createApiService<VocabularyService>(get()) }
//
//    //EmailValidator
//    single<EmailValidator> { EmailValidatorImp() }
}