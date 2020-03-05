package com.dataductus.learn;

import com.tailf.conf.ConfException;
import com.tailf.dp.annotations.ServiceCallback;
import com.tailf.dp.proto.ServiceCBType;
import com.tailf.dp.services.ServiceContext;
import com.tailf.navu.NavuException;
import com.tailf.navu.NavuNode;
import com.tailf.ncs.PlanComponent;
import com.tailf.ncs.template.Template;
import com.tailf.ncs.template.TemplateVariables;
import java.util.Properties;

/**
 *
 * @author magnus
 */
public class RegisterPersonRFS {

    /**
     *
     * Executed at 'commit'
     *
     * Create and PreLockCreate are mutually exclusive for a service.
     *
     * @param context
     * @param service - service "Yang"
     * @param ncsRoot
     * @param opaque
     * @return
     * @throws ConfException
     */
    @ServiceCallback(servicePoint = "register-person-servicepoint", callType = ServiceCBType.CREATE)
    public Properties create(ServiceContext context, NavuNode service, NavuNode ncsRoot, Properties opaque) throws ConfException {
        System.out.println("CREATE:RegisterPersonRFS");
        String personClass = service.leaf("class").valueAsString();
        if ("common".equals(personClass)) {
            Template template = new Template(context, "people-common");
            TemplateVariables templateVariables = new TemplateVariables();
            templateVariables.putQuoted("GIVEN_NAME", service.leaf("givenname").valueAsString());
            templateVariables.putQuoted("SURNAME", service.leaf("surname").valueAsString());
            templateVariables.putQuoted("ID", service.leaf("id").valueAsString());
            template.apply(service, templateVariables);
        } else if ("noble".equals(personClass)) {
            Template template = new Template(context, "people-noble");
            TemplateVariables templateVariables = new TemplateVariables();
            templateVariables.putQuoted("GIVEN_NAME", service.leaf("givenname").valueAsString());
            templateVariables.putQuoted("SURNAME", service.leaf("surname").valueAsString());
            templateVariables.putQuoted("ID", service.leaf("id").valueAsString());
            template.apply(service, templateVariables);
        }
        return opaque;
    }
}
