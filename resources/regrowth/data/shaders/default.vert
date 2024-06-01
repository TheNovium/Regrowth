#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 texCoord;
layout (location = 3) in float rotation;
layout (location = 4) in float id;

uniform mat4 ortho_matrix;
uniform mat4 view_matrix;

out DATA {
    vec4 color;
    vec3 pos;
    float id;
    float rotation;
} fs_out;

void main() {
    fs_out.color = color;
    fs_out.pos = vec3(texCoord, pos.z);
    fs_out.id = id;

    gl_Position = ortho_matrix * view_matrix * vec4(pos, 1.0);
}