onerror {quit -f}
vlib work
vlog -work work simpleCPU.vo
vlog -work work simpleCPU.vt
vsim -novopt -c -t 1ps -L cycloneii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.simpleCPU_vlg_vec_tst
vcd file -direction simpleCPU.msim.vcd
vcd add -internal simpleCPU_vlg_vec_tst/*
vcd add -internal simpleCPU_vlg_vec_tst/i1/*
add wave /*
run -all
