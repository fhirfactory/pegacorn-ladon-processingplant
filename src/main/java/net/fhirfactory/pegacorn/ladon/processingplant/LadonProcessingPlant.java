package net.fhirfactory.pegacorn.ladon.processingplant;

import net.fhirfactory.pegacorn.common.model.FDN;
import net.fhirfactory.pegacorn.common.model.RDN;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElement;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementFunctionToken;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementIdentifier;
import net.fhirfactory.pegacorn.petasos.model.topology.NodeElementTypeEnum;
import net.fhirfactory.pegacorn.processingplatform.CoreInternalSubsystemProcessingPlatform;
import net.fhirfactory.pegacorn.processingplatform.common.StandardProcessingPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LadonProcessingPlant extends CoreInternalSubsystemProcessingPlatform {

    private static final Logger LOG = LoggerFactory.getLogger(LadonProcessingPlant.class);

    private static String LADON_VERSION = "1.0.0";
    private NodeElementFunctionToken nodeToken;
    private NodeElementIdentifier nodeIdentifier;
    private boolean isInitialised;

    @Override
    protected String specifyProcessingPlantName(){
        return(getSubsystemComponentNames().getLadonProcessingPlantDefault());
    }

    @Override
    protected String specifyProcessingPlantVersion(){
        return(LADON_VERSION);
    }

    @Override
    protected String specifySite() {
        return "___";
    }

    @Override
    protected String specifyPlatform() {
        return "___";
    }

    public NodeElementFunctionToken getNodeElementFunctionToken() {
        return (this.nodeToken);
    }

    public NodeElementIdentifier getNodeIdentifier() {
        return (this.nodeIdentifier);
    }

    @Override
    protected void buildProcessingPlantWorkshops() {
        LOG.debug(".buildLadonWorkshops(): Entry");
        LOG.trace(".buildLadonWorkshops(): 1st, the DTCache!");
        if(LOG.isTraceEnabled()) {
            LOG.trace(".buildLadonWorkshops(): ProcessingPlant Identifier --> {}", this.getProcessingPlantNodeId());
            LOG.trace(".buildLadonWorkshops(): ProcessingPlant NodeElement --> {}", this.getProcessingPlantNodeElement());
        }
        FDN dtcacheFDN = new FDN(this.getProcessingPlantNodeId());
        dtcacheFDN.appendRDN(new RDN(NodeElementTypeEnum.WORKSHOP.getNodeElementType(), "DTCache"));
        NodeElementIdentifier dtcacheId = new NodeElementIdentifier(dtcacheFDN.getToken());
        NodeElement dtcache = new NodeElement();
        dtcache.setVersion(LADON_VERSION);
        dtcache.setNodeInstanceID(dtcacheId);
        FDN dtcacheFunctionFDN = new FDN(this.getProcessingPlantNodeElement().getNodeFunctionID());
        dtcacheFunctionFDN.appendRDN(new RDN(NodeElementTypeEnum.WORKSHOP.getNodeElementType(), "DTCache"));
        dtcache.setNodeFunctionID(dtcacheFunctionFDN.getToken());
        dtcache.setConcurrencyMode(this.getProcessingPlantNodeElement().getConcurrencyMode());
        dtcache.setResilienceMode(this.getProcessingPlantNodeElement().getResilienceMode());
        dtcache.setInstanceInPlace(true);
        dtcache.setContainingElementID(this.getProcessingPlantNodeId());
        this.getDeploymentIM().registerNode(dtcache);
        this.getDeploymentIM().addContainedNodeToNode(this.getProcessingPlantNodeId(),dtcache);
        LOG.trace(".buildLadonWorkshops(): 2nd, the Edge");
        FDN edgeFDN = new FDN(this.getProcessingPlantNodeId());
        edgeFDN.appendRDN(new RDN(NodeElementTypeEnum.WORKSHOP.getNodeElementType(), "Edge"));
        NodeElementIdentifier edgeId = new NodeElementIdentifier(edgeFDN.getToken());
        NodeElement edge = new NodeElement();
        edge.setVersion(LADON_VERSION);
        edge.setNodeInstanceID(edgeId);
        FDN edgeFunctionFDN = new FDN(this.getProcessingPlantNodeElement().getNodeFunctionID());
        edgeFunctionFDN.appendRDN(new RDN(NodeElementTypeEnum.WORKSHOP.getNodeElementType(), "Edge"));
        edge.setNodeFunctionID(edgeFunctionFDN.getToken());
        edge.setConcurrencyMode(this.getProcessingPlantNodeElement().getConcurrencyMode());
        edge.setResilienceMode(this.getProcessingPlantNodeElement().getResilienceMode());
        edge.setInstanceInPlace(true);
        edge.setContainingElementID(this.getProcessingPlantNodeId());
        this.getDeploymentIM().registerNode(edge);
        this.getDeploymentIM().addContainedNodeToNode(this.getProcessingPlantNodeId(),edge);
        LOG.debug(".buildLadonWorkshops(): Exit");
    }
}
