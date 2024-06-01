#version 330 core

in DATA {
    vec4 color;
    vec3 pos;
    float id;
    float rotation;
} fs_in;

uniform sampler2D textures[8];

out vec4 f_color;

void main() {
    int id = int(fs_in.id);
    gl_FragDepth = fs_in.pos.z;
    if(id == 0){
        f_color = fs_in.color;
    } else {
        f_color = fs_in.color * texture(textures[id], fs_in.pos.xy);
    }
}