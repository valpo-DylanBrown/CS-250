require 'erb'
# Reading the Federalist Papers
#
# Fed object with attibutes:
# fedNum  - Federalist Number
# fedTitle - Title of Paper
# fedPub - Publisher of Paper
# fedAuthor - Author of Paper
#
class Fed
    attr_accessor :fedNum
    attr_accessor :fedTitle
    attr_accessor :fedPub
    attr_accessor :fedAuthor
    attr_accessor :fedDate
    # Constructor
    def initialize
      @fedNum=0
      @fedTitle=""
      @fedPub=""
      @fedAuthor=""
      @fedDate=""
    end

    # Method to print data on one Fed object
    def prt
       puts "Federalist #@fedNum"
       puts "Title: #{@fedTitle}"
       puts "Pub: #{@fedPub}"
       puts "Author: #{@fedAuthor}"
       puts "Date: #{@fedDate}"
       puts "\n\n\n"
    end

    def get_binding
      binding
    end
end

#=========================
# Main program
#=========================

# Input will come from file fed.txt
file = File.new("fed.txt", "r")

# List of Fed objects
feds = []
curFed = nil
readstate = 'b4Fed'
# Read and process each line
while (line = file.gets)
    line.strip!            # Remove trailing white space
    words = line.split     # Split into array of words

    #if words.length == 0 then
      #next
    #end

    if curFed.nil?
      # "FEDERALIST No. number" starts a new Fed object
      if (readstate=='b4Fed' && words[0]=="FEDERALIST") then
         curFed = Fed.new    # Construct new Fed object
         feds << curFed      # Add it to the array
         curFed.fedNum = words[2] # The number is always in the second posistion
         readstate = 'b4TitlePub' # change the state of the program
         next
      end
    end

    if !curFed.nil?

      if(readstate=='b4TitlePub' && !words.empty?)
        curFed.fedTitle = words.join(' ') # Add the title to the string
        readstate = 'inTitlePub' # change the state

        next
      end

      if(readstate=='inTitlePub' && !words.empty?)
        curFed.fedTitle = curFed.fedTitle + "\n" + words.join(' ') # add more title to the string for multiline titles
        next
      end

      if(readstate=='inTitlePub' && words.empty?)
        readstate = 'b4Author'  # if the line is empty, change the state
        next
      end

      if(readstate=='b4Author' && !words.empty?)
        # Error checking
        if(words[0] == "For" || words[0] == "From") then
          curFed.fedTitle = curFed.fedTitle + "\n" + words.join(' ')
          next
        end
        # SET author
        curFed.fedAuthor = words.join(' ')
        readstate = 'b4Fed'
        # CLEAN up title and publisher before setting to nil

        titleLines = curFed.fedTitle.lines.map(&:chomp)
        #while(titleLines)
          #titleLines.strip!
          #words = titleLines.split

        #end
        i = 1
        until i > 2
          if ((titleLines.last.include? "For") || (titleLines.last.include? "From")) then
            curFed.fedPub = titleLines.last
            curFed.fedPub.gsub!("For ", "")
            curFed.fedPub.gsub!("From ", "")
            curFed.fedPub.gsub!("the ", "")
            #curFed.fedPub = titleLines.last
            list1 = curFed.fedPub.split(".")
            curFed.fedPub = list1[0]
            if(curFed.fedDate.empty?) then
              curFed.fedDate = list1[1]

            end
            titleLines.pop
            curFed.fedTitle = titleLines.join("\n")
            break
          elsif ((titleLines.last.include? "Tuesday") || (titleLines.last.include? "Wednesday") || (titleLines.last.include? "Thursday") || (titleLines.last.include? "Friday") || (titleLines.last.include? "January")) then
            curFed.fedDate = titleLines.last
            curFed.fedDate.gsub!(".", "")
            titleLines.pop
            i = i+1
          else
            curFed.fedTitle = titleLines.join("\n")
            break
          end
        end

        # SET object to nil
        curFed = nil
        next
      end

    end

end # End of reading

file.close



# Apply the prt (print) method to each Fed object in the feds array
feds.each{|f| f.prt}
template = File.read("./template.html.erb")
#rhtml = ERB.new(template)
renderer = ERB.new(template)
#result = renderer.result(feds[0].get_binding)

#feds.each{|f| rhtml.run(f.get_binding)}

File.open('temp.html', 'w+' ) do |temp|
  #f.write(rhtml.run(feds[0].get_binding))
  feds.each{|f| temp.write renderer.result(f.get_binding)}
  #f.write renderer.result(feds[0].get_binding)
end

#puts"#{readstate}"
