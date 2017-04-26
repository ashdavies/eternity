package io.ashdavies.eternity.inject;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.ashdavies.eternity.chat.ChatActivity;
import io.ashdavies.eternity.firebase.MessageService;
import io.ashdavies.eternity.signin.SignInActivity;

@Module
abstract class AndroidBindingModule {

  @ContributesAndroidInjector
  abstract ChatActivity chatActivity();

  @ContributesAndroidInjector
  abstract SignInActivity signInActivity();

  @ContributesAndroidInjector
  abstract MessageService messageService();
}
