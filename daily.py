import subprocess
import os
import shutil

frontend_jar = os.path.join(os.getcwd(), "frontend", "target", "frontend.jar")
backoffice_jar = os.path.join(os.getcwd(), "backoffice", "target", "backoffice.jar")
vsl = os.path.join(os.getcwd(), "files", "vsl.txt")

def session( day_num, session_num ):
    instructions_set = "day" + str(day_num) + "-" + str(session_num) + ".txt"
    instructions_set_dir = "day" + str(day_num)
    instructions_file = os.path.join(os.getcwd(), "files", "Transaction_Inputs", instructions_set_dir, instructions_set)
    instructions = ""
    with open(instructions_file, "rb") as f:
        instructions = f.read()

    tsf_name = "tsf-" + str(day_num) + "-" + str(session_num) + ".txt"
    out = os.path.join(os.getcwd(), "files", "temp", tsf_name)
    with open("/dev/null", "w+") as err:
        subprocess.run(["java", "-jar", frontend_jar, vsl, out], input=instructions, stderr=err)

    return out

def day( day_num ):
    merged_name = "merged.txt"
    merged_file = os.path.join(os.getcwd(), "files", "temp", merged_name)
    with open(merged_file, "a") as mtsf:
        for n in range(1, 4):
            with open(session(day_num, n), "r") as tsf:
                mtsf.write(tsf.read())

    csf = os.path.join(os.getcwd(), "files", "csf.txt")
    with open("/dev/null", "w+") as err:
        subprocess.run(["java", "-jar", backoffice_jar, merged_file, csf, csf, vsl], stderr=err)

    temp_dir = os.path.join(os.getcwd(), "files", "temp")
    shutil.rmtree(temp_dir)
    os.mkdir(temp_dir)

    return