package org.ei.drishti.view.dialog;

import android.graphics.drawable.Drawable;
import android.view.View;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.contract.ServiceProvidedDTO;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;
import static org.ei.drishti.view.contract.ChildSmartRegisterClient.SickStatus;

public class ChildOverviewServiceMode extends ServiceModeOption {

    public ChildOverviewServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.child_service_mode_overview);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 6;
            }

            @Override
            public int weightSum() {
                return 100;
            }

            @Override
            public int[] weights() {
                return new int[]{30, 15, 15, 15, 15, 10};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_id_no, R.string.header_dob,
                        R.string.header_last_service, R.string.header_sick_status, R.string.header_edit};
            }
        };
    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {
        viewHolder.serviceModeOverviewView().setVisibility(VISIBLE);

        setupDobView(client, viewHolder);
        setupLastServiceView(client, viewHolder);
        setupSickStatus(client, viewHolder, clientSectionClickListener);
        setupEditView(client, viewHolder, clientSectionClickListener);
    }

    private void setupDobView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        viewHolder.dobView().setText(client.dateOfBirth());
    }

    private void setupLastServiceView(ChildSmartRegisterClient client, NativeChildSmartRegisterViewHolder viewHolder) {
        ServiceProvidedDTO lastService = client.lastServiceProvided();
        viewHolder.lastServiceDateView().setText(lastService.date());
        viewHolder.lastServiceNameView().setText(lastService.type().displayName());
    }

    private void setupSickStatus(ChildSmartRegisterClient client,
                                 NativeChildSmartRegisterViewHolder viewHolder,
                                 View.OnClickListener onClickListener) {
        final SickStatus sickStatus = client.sickStatus();
        if (sickStatus == SickStatus.noDiseaseStatus) {
            viewHolder.sickVisitView().setVisibility(VISIBLE);
            viewHolder.sickVisitView().setTag(client);
            viewHolder.sickVisitView().setOnClickListener(onClickListener);
            viewHolder.sicknessDetailLayout().setVisibility(GONE);
        } else {
            viewHolder.sickVisitView().setVisibility(GONE);
            viewHolder.sicknessDetailLayout().setVisibility(VISIBLE);
            viewHolder.illnessView().setText(sickStatus.diseases());
            viewHolder.illnessDateView().setText(sickStatus.date());
        }
    }

    private void setupEditView(ChildSmartRegisterClient client,
                               NativeChildSmartRegisterViewHolder viewHolder,
                               View.OnClickListener onClickListener) {
        Drawable iconPencilDrawable = Context.getInstance().applicationContext().getResources().getDrawable(R.drawable.ic_pencil);
        viewHolder.editButton().setImageDrawable(iconPencilDrawable);
        viewHolder.editButton().setOnClickListener(onClickListener);
        viewHolder.editButton().setTag(client);
    }
}
