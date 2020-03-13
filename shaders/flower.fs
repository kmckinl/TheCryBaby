#version 330 core

layout (location=0) out vec4 color;

in DATA
{
	vec2 tc;
} fs_in;

uniform vec2 texOffset;
uniform sampler2D tex;

void main()
{
	color = texture(tex, fs_in.tc + texOffset);
	if(color.w < 1.0f)
		discard;
}