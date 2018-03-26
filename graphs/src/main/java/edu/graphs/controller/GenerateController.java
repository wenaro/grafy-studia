package edu.graphs.controller;

import edu.graphs.input.Form;
import edu.graphs.input.Input;
import edu.graphs.input.Type;
import edu.graphs.model.Graph;
import edu.graphs.service.EdgeService;
import edu.graphs.service.GraphService;
import edu.graphs.service.VertexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GenerateController {

    private final GraphService graphService;

    private final VertexService vertexService;

    private final EdgeService edgeService;

    public GenerateController(final GraphService graphService, final VertexService vertexService,
                              final EdgeService edgeService) {
        this.graphService = graphService;
        this.vertexService = vertexService;
        this.edgeService = edgeService;
    }

    private Graph graph = new Graph();

    private Input input = new Input();

    @GetMapping("/graph")
    public String generate(final Model model) {
        addModelAttributes(model, new Input(), new Form());
        return "generate";
    }

    @PostMapping(value = "/graph",
                 params = "action=generate")
    public String submitInput(final Model model, @ModelAttribute final Input input) {
        this.input = input;
        graph = graphService.createGraph(input.getType(), input.getText(), input);
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/graph",
                 params = "action=clear")
    public String clearGraph(final Model model, @ModelAttribute final Input input) {
        graph = new Graph();
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/graph",
                 params = "action=update")
    public String updateGraph(final Model model, @ModelAttribute final Input input) {
        graph = graphService.createGraph(Type.NEIGHBORHOOD_LIST, input.getConversion(), input);
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/vertex",
                 params = "action=add")
    public String addVertex(final Model model, @ModelAttribute final Form form) {
        graph.addVertex(form.getVertex());
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/update",
                 params = "action=update")
    public String updateVertex(final Model model, @ModelAttribute final Form form) {
        vertexService.updateVertex(graph, form);
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/vertex",
                 params = "action=remove")
    public String removeVertex(final Model model, @ModelAttribute final Form form) {
        vertexService.removeVertex(graph, form);
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/edge",
                 params = "action=add")
    public String addEdge(final Model model, @ModelAttribute final Form form) {
        edgeService.addFormEdge(graph, form);
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    @PostMapping(value = "/edge",
                 params = "action=remove")
    public String removeEdge(final Model model, @ModelAttribute final Form form) {
        edgeService.removeEdge(graph, form);
        addModelAttributes(model, input, new Form());
        return "generate";
    }

    private void addModelAttributes(final Model model, final Input input, final Form form) {
        model.addAttribute("input", input);
        model.addAttribute("vertices", graph.getVertices());
        model.addAttribute("edges", graph.getEdges());
        model.addAttribute("form", form);
    }
}
