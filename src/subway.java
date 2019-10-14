public class subway {
    public static void main(String[] args) throws Exception {
        if(args[0].equals("-map")){
            Run.load(args[1]);
        }
        else if(args[0].equals("-a")){
            if(!args[2].equals("-map") || !args[4].equals("-o")){
                throw new Exception("输入参数有误！");
            }
            else
                Run.line(args[3], args[1], args[5]);
        }
        else if(args[0].equals("-b")){
            if(!args[3].equals("-map") || !args[5].equals("-o")){
                throw new Exception("输入参数有误！");
            }
            else
                Run.serach(args[4], args[1], args[2], args[6]);
        }
        else
            throw new Exception("输入参数有误！");
    }
}
