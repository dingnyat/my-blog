package ding.nyat.controller.workspace;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkspaceController {
    @GetMapping(value = {"/workspace"})
    public String dashboard() {
        return "workspace/workspace";
    }
}
