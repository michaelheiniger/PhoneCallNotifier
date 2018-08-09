package ch.qscqlmpa.phonecallnotifier.home;

import ch.qscqlmpa.phonecallnotifier.ProcessIncomingCallIS;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {

})
public interface ServiceComponent extends AndroidInjector<ProcessIncomingCallIS> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ProcessIncomingCallIS> {

        @Override
        public void seedInstance(ProcessIncomingCallIS instance) {
        }
    }
}
